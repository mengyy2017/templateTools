package com.spider.pub.conf.elasticsearch;

import com.common.bussiness.entity.BaseEntity;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EClient {

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
        Stream<Field> reflectFields = getReflectFileds(t);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("properties");
            reflectFields.forEach(acceptOrThrow(field -> {
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
        }

    }

    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {

            AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
            if (response.isAcknowledged())
                System.out.println("{} 索引删除成功！" + indexName);

        } catch (ElasticsearchException ex) {
            if (ex.status() == RestStatus.NOT_FOUND)
                System.out.println("{} 索引名不存在" + indexName);
            System.out.println("删除失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T extends BaseEntity> void addDoc(String indexName, T data) {
        Stream<Field> reflectFields = getReflectFileds(data.getClass());
        IndexRequest request = new IndexRequest(indexName);

        try {

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.forEach(acceptOrThrow(field ->
                builder.field(field.getName(), field.get(data))
            ));
            builder.endObject();

            request.id(data.getId()).source(builder);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

            if (response.getResult() == DocWriteResponse.Result.CREATED)
                System.out.println("新增文档成功！");
            else if (response.getResult() == DocWriteResponse.Result.UPDATED)
                System.out.println("修改文档成功！");

        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT)
                System.out.println("版本异常！");
            System.out.println("文档新增失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T extends BaseEntity> void bulkAddDoc(String indexName, List<T> dataList) throws Exception {
        if(dataList == null || dataList.size() < 1)
            throw new Exception("数据不能为空");

        Stream<Field> reflectFields = getReflectFileds(dataList.get(0).getClass());

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> indexRequstList = dataList.stream().map(applyOrThrow(data -> {
            IndexRequest indexRequest = new IndexRequest(indexName);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            reflectFields.forEach(acceptOrThrow(field ->
                builder.field(field.getName(), field.get(data))
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
                    System.out.println("\"index={}, type={}, id={}\"的文档增加失败！" + failure.getIndex() + failure.getType() + failure.getId());
                    System.out.println("增加失败详情: {}" + failure.getMessage());
                } else
                    System.out.println("\"index={}, type={}, id={}\"的文档增加成功！" + bulkItemResponse.getIndex() + bulkItemResponse.getType() + bulkItemResponse.getId());
            }
        }
    }

    private static Stream<Field> getReflectFileds(Class t){
        return t != null ? Arrays.stream(t.getDeclaredFields()) : Stream.of();
    }

    public <T> Consumer<T> acceptOrThrow(CheckedConsumer<T> checkedConsumer){
        return t -> {
            try {
                checkedConsumer.accept(t);
            } catch (Exception e){
                e.printStackTrace();;
                throw new RuntimeException(e.getMessage());
            }
        };
    }

    public <T, R> Function<T, R> applyOrThrow(CheckedFuntion<T, R> checkedFuntion){
        return t -> {
            try {
                return checkedFuntion.apply(t);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        };
    }

}

@FunctionalInterface
interface CheckedConsumer<T>{
    void accept(T t) throws Exception;
}

@FunctionalInterface
interface CheckedFuntion<T, R>{
    R apply(T t) throws Exception;
}
