package com.spider.pub.conf.elasticsearch;

import com.spider.bussiness.entity.UInfo;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EClient {

    public static void main(String[] args) {
        EClient eClient = new EClient();
        try {
            eClient.createIndex("u_info", UInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RestHighLevelClient esClient;

    static {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public <T> void createIndex(String indexName, Class t) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Field[] reflectFields = getReflectFileds(t);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
            builder.startObject("properties");
            Arrays.stream(reflectFields).forEach(acceptOrThrow(field -> {
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
        if (acknowledged || shardsAcknowledged) {
            System.out.println("创建索引成功！索引名称为{}" + indexName);
        }
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

    public <T> void bulkAdd(List<T> dataList) throws Exception {
        if(dataList == null || dataList.size() < 1)
            throw new Exception("数据不能为空");

        Field[] reflectFields = getReflectFileds(dataList.get(0).getClass());

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> indexRequstList = dataList.stream().map(data -> {
            DocWriteRequest<?> indexRequest = new IndexRequest();
            Arrays.stream(reflectFields).map(field -> null);
            return indexRequest;
        }).collect(Collectors.toList());

        bulkRequest.add(indexRequstList);

    }

    private static Field[] getReflectFileds(Class t){
        return t != null ? t.getDeclaredFields() : new Field[]{};
    }

}

@FunctionalInterface
interface CheckedConsumer<T>{
    void accept(T t) throws Exception;
}
