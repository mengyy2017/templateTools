package com.spider.pub.conf.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EClient {

    private RestHighLevelClient esClient;

    @PostConstruct
    private void init(){
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public void createIndex(String indexName){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);

    }

    public <T> void bulkAdd(List<T> dataList) throws Exception {
        if(dataList == null)
            throw new Exception("数据不能为空");

        Field[] reflectFields = getReflectFileds(dataList);

        BulkRequest bulkRequest = new BulkRequest();

        List<DocWriteRequest<?>> indexRequstList = dataList.stream().map(data -> {
            DocWriteRequest indexRequest = new IndexRequest();
            Arrays.stream(reflectFields).map(field -> null);
            return indexRequest;
        }).collect(Collectors.toList());

        bulkRequest.add(indexRequstList);

    }

    private static <T> Field[] getReflectFileds(List<T> dataList){
        for(T t: dataList)
            return t != null ? t.getClass().getDeclaredFields() : new Field[]{};
        return new Field[]{};
    }

}
