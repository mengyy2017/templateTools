package com.spider.pub.conf.elasticsearch;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubInter.ChildAnnotation;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EClient extends CheckedUtil {

    public static void main(String[] args) {
        EClient eClient = new EClient();
//        eClient.createIndex("u_info", UInfo.class);
        eClient.deleteIndex("u_info");
    }

    private static RestHighLevelClient esClient;

    static {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public void createIndex(String indexName, Class t) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        List<Field> reflectFields = getReflectFileds(t).collect(Collectors.toList());
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            String parentName = ((Table)t.getAnnotation(Table.class)).name();
            Stream<Field> childAnnoField = reflectFields.stream().filter(f -> f.isAnnotationPresent(ChildAnnotation.class));
            builder.startObject();
            builder.startObject("properties");
                builder.startObject("join_field");
                    builder.field("type", "join");
                    builder.startObject("relations");
                        childAnnoField.forEach(acceptOrThrow(f -> {
                            builder.field(parentName, f.getAnnotation(ChildAnnotation.class).name());
                        }));
                    builder.endObject();
                builder.endObject();
            reflectFields.stream().filter(f -> !f.isAnnotationPresent(ChildAnnotation.class))
                .forEach(acceptOrThrow(field -> {
                    builder.startObject(field.getName());
                    builder.field("type", "text");
                    builder.endObject();
                }));
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

    public <T extends BaseEntity> void addDoc(String indexName, T data) {
        Stream<Field> reflectFields = getReflectFileds(data.getClass());
        IndexRequest request = new IndexRequest(indexName);

        try {

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.forEach(acceptOrThrow(field ->{
                    field.setAccessible(Boolean.TRUE);
                    builder.field(field.getName(), field.get(data));
                }
            ));
            builder.endObject();

            request.id(data.getId()).source(builder);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

            if (response.getResult() == DocWriteResponse.Result.CREATED)
                System.out.println("新增文档成功！");
            else if (response.getResult() == DocWriteResponse.Result.UPDATED)
                System.out.println("修改文档成功！");

        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.CONFLICT)
                throw new RuntimeException("版本异常！");
            throw new RuntimeException("文档新增失败！");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public <T extends BaseEntity> void bulkAddDoc(String indexName, List<T> dataList) throws Exception {
        if(dataList == null || dataList.size() < 1)
            throw new Exception("数据不能为空");

        List<Field> reflectFields = getReflectFileds(dataList.get(0).getClass()).collect(Collectors.toList());

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> indexRequstList = dataList.stream().map(applyOrThrow(data -> {
            IndexRequest indexRequest = new IndexRequest(indexName);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.stream().forEach(acceptOrThrow(field -> {
                    field.setAccessible(Boolean.TRUE);
                    builder.field(field.getName(), field.get(data));
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

    private static Stream<Field> getReflectFileds(Class t){
        return t != null ? Arrays.stream(t.getDeclaredFields()) : Stream.of();
    }

    private static int getFiledSort(Field field){
        return field.isAnnotationPresent(ChildAnnotation.class) ? 0 : 1;
    }

}


