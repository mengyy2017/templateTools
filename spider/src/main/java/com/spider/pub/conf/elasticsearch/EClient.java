package com.spider.pub.conf.elasticsearch;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubInter.BiCheckedFunction;
import com.common.pub.pubInter.ChildAnnotation;
import com.common.pub.pubInter.ESIdAnnotation;
import com.common.pub.pubInter.ForeignKeyAnnotation;
import com.common.util.CheckedUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Component;
import javax.persistence.Table;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EClient extends CheckedUtil {

    private static String join_field = "join_field";

    public static void main(String[] args) {
        EClient eClient = new EClient();
//        eClient.createIndex("u_info", UInfo.class);
        eClient.deleteIndex("u_info");
    }

    private static RestHighLevelClient esClient;

    static {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public void createIndex(String indexName, Class clazz) {

        CreateIndexRequest request = new CreateIndexRequest(indexName);

        List<Field> reflectFieldList = getReflectFileds(clazz);

        String parentName = ((Table)clazz.getAnnotation(Table.class)).name();

        List<Field> parentFieldList = filterCreateIndexField(reflectFieldList, Boolean.TRUE);

        List<Field> childAnnoFieldList = filterCreateIndexField(reflectFieldList, Boolean.FALSE);

        Map<String, String[]> relationshipMap = new HashMap<>();

        try {

            XContentBuilder builder = XContentFactory.jsonBuilder();

            builder.startObject();
            builder.startObject("properties");

                buildChildObjField(builder, childAnnoFieldList, parentName, relationshipMap); // 构建每个子关系字段 以及递归构建每个子关系的子关系字段

                buildObjField(builder, parentName, parentFieldList); // 构建父关系字段

                buildRelationship(builder, relationshipMap); // 构建关系

            builder.endObject();
            builder.endObject();

            request.mapping(builder);
            CreateIndexResponse response =  esClient.indices().create(request, RequestOptions.DEFAULT);
            // 指示是否所有节点都已确认请求
            boolean acknowledged = response.isAcknowledged();
            // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            if (acknowledged || shardsAcknowledged)
                System.out.println("创建索引成功！索引名称为{}" + indexName);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public <T extends BaseEntity> void addDoc(String indexName, T data) {

        addDocExecute(indexName, data, (t, u) -> {
            IndexRequest request = new IndexRequest(t);

            Class clazz = u.getClass();

            List<Field> reflectFields = getReflectFileds(clazz);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.forEach(acceptOrThrow(field -> {
                        field.setAccessible(Boolean.TRUE);
                        builder.field(field.getName(), field.get(data).toString());
                    }
            ));
            builder.endObject();

            request.id(data.getId()).source(builder);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            return response;
        });

    }

    public <T extends BaseEntity> void addRelationDoc(String indexName, T data, Boolean isChild) {
        addDocExecute(indexName, data, (t, u) -> {
            IndexRequest request = new IndexRequest(t);

            Class clazz = u.getClass();

            String objName = ((Table)clazz.getAnnotation(Table.class)).name();
            final String[] keyAndForeignKey = {"", ""};

            List<Field> reflectFields = filterField(getReflectFileds(clazz), Boolean.TRUE);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
                reflectFields.forEach(acceptOrThrow(field -> {
                        field.setAccessible(Boolean.TRUE);
                        builder.field(field.getName(), field.get(data)).toString();
                        if(field.isAnnotationPresent(ESIdAnnotation.class))
                            keyAndForeignKey[0] = field.get(data).toString();
                        if(isChild && field.isAnnotationPresent(ForeignKeyAnnotation.class))
                            keyAndForeignKey[1] = field.get(data).toString();
                    }
                ));
                builder.startObject(join_field);
                    builder.field("name", objName);
                    if(isChild) builder.field("parent", keyAndForeignKey[1]);
                builder.endObject();
            builder.endObject();

            request.id(keyAndForeignKey[0]).source(builder);
            if(isChild) request.routing(keyAndForeignKey[1]);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            return response;
        });
    }

    private <U extends BaseEntity, R extends DocWriteResponse> void addDocExecute(String indexName, U data, BiCheckedFunction<String, U, R> biCheckedFunction){
        try {

            R response = biCheckedFunction.biApply(indexName, data);

            if (response.getResult() == DocWriteResponse.Result.CREATED)
                System.out.println("新增文档成功！");
            else if (response.getResult() == DocWriteResponse.Result.UPDATED)
                System.out.println("修改文档成功！");

        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.CONFLICT)
                throw new RuntimeException("版本异常！");
            throw new RuntimeException("文档新增失败！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public <T extends BaseEntity> void bulkAddDoc(String indexName, List<T> dataList) throws Exception {
        if(dataList == null || dataList.size() < 1)
            throw new Exception("数据不能为空");

        List<Field> reflectFields = getReflectFileds(dataList.get(0).getClass());

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> indexRequstList = dataList.stream().map(applyOrThrow(data -> {
            IndexRequest indexRequest = new IndexRequest(indexName);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.stream().forEach(acceptOrThrow(field -> {
                    field.setAccessible(Boolean.TRUE);
                    builder.field(field.getName(), field.get(data).toString());
                }
            ));
            builder.endObject();

            indexRequest.id(data.getId()).source(builder);
            return indexRequest;
        })).collect(Collectors.toList());

        bulkRequest.add(indexRequstList);
        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        // 全部操作成功
        if (!response.hasFailures())
            System.out.println("批量增加操作成功！");
        else {
            for (BulkItemResponse bulkItemResponse : response) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    throw new RuntimeException("\"index={}, type={}, id={}\"的文档增加失败！"
                            + failure.getIndex() + failure.getType() + failure.getId()
                            + "增加失败详情: {}" + failure.getMessage());
                } else
                    System.out.println("\"index={}, type={}, id={}\"的文档增加成功！" + bulkItemResponse.getIndex() + bulkItemResponse.getType() + bulkItemResponse.getId());
            }
        }
    }

    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {

            AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
            if (response.isAcknowledged())
                System.out.println("{} 索引删除成功！" + indexName);

        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.NOT_FOUND)
                throw new RuntimeException("{} 索引名不存在" + indexName);
            throw new RuntimeException("删除失败！");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteDoc(String indexName, String id){
        DeleteRequest request = new DeleteRequest(indexName, id);
        try {
            DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            // if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            //     throw new RuntimeException("分片未全部删除成功!");
            // }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures())
                    throw new RuntimeException(failure.reason());
            }

            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND)
                throw new RuntimeException("文档未找到，删除失败！");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public <T extends BaseEntity> void bulkDeleteDoc(String indexName, List<T> dataList) throws Exception {
        if(dataList == null || dataList.size() < 1)
            throw new Exception("数据不能为空");

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> deleteRequstList = dataList.stream().map(applyOrThrow(data -> {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, data.getId());
            return deleteRequest;
        })).collect(Collectors.toList());

        bulkRequest.add(deleteRequstList);
        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        // 全部操作成功
        if (!response.hasFailures())
            System.out.println("批量删除操作成功！");
        else {
            for (BulkItemResponse bulkItemResponse : response) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    throw new RuntimeException("\"index={}, type={}, id={}\"的文档删除失败！"
                            + failure.getIndex() + failure.getType() + failure.getId()
                            + "删除失败详情: {}" + failure.getMessage());
                } else
                    System.out.println("\"index={}, type={}, id={}\"的文档删除成功！" + bulkItemResponse.getIndex() + bulkItemResponse.getType() + bulkItemResponse.getId());
            }
        }
    }

    private static List<Field> getReflectFileds(Class clazz){
        return clazz != null ? Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()) : Collections.emptyList();
    }

    private List<Field> filterField(List<Field> fieldList, Boolean isFilterChildAnno){
        return fieldList.stream().filter(f ->
                isFilterChildAnno ^ f.isAnnotationPresent(ChildAnnotation.class)
        ).collect(Collectors.toList());
    }

    private List<Field> filterCreateIndexField(List<Field> fieldList, Boolean isFilterChildAnno){
        return fieldList.stream().filter(f ->
                (isFilterChildAnno ^ f.isAnnotationPresent(ChildAnnotation.class)) && !f.isAnnotationPresent(ForeignKeyAnnotation.class)
        ).collect(Collectors.toList());
    }

    private void buildRelationship(XContentBuilder builder, Map<String, String[]> relationshipMap) throws IOException {
        builder.startObject(join_field); // 构建关系
        builder.field("type", "join");
        builder.startObject("relations");
        relationshipMap.forEach(biAcceptOrThrow((parentName, childNameArr) -> {
            builder.array(parentName, childNameArr);
        }));
        builder.endObject();
        builder.endObject();
    }

    private void buildChildObjField(XContentBuilder builder, List<Field> childAnnoFieldList, String parentName, Map<String, String[]> relationshipMap){
        String[] relationshipArr = childAnnoFieldList.stream().map(applyOrThrow(f -> { // 构建每个子关系字段
                Class childClazz = (Class) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
                List<Field> childReflectFields = filterCreateIndexField(getReflectFileds(childClazz), true);
                String childObjName = f.getAnnotation(ChildAnnotation.class).name();
                buildObjField(builder, childObjName, childReflectFields);

                List<Field> childAnnoFields = filterCreateIndexField(getReflectFileds(childClazz), false);
                if (childAnnoFields.size() > 0)
                    buildChildObjField(builder, childAnnoFields, childObjName, relationshipMap);
                return childObjName;
            })
        ).toArray(String[]::new);
        relationshipMap.put(parentName, relationshipArr);
    }

    private void buildObjField(XContentBuilder builder, String objName, List<Field> fieldList) throws IOException {
        fieldList.forEach(acceptOrThrow(field ->
            buildEachFieldType(builder, field))
        );
    }

    private void buildEachFieldType(XContentBuilder builder, Field field) throws IOException {
        builder.startObject(field.getName());
        builder.field("type", "text");
        builder.endObject();
    }
}


