package com.spider.pub.conf.elasticsearch;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubInter.*;
import com.common.util.CheckedUtil;
import com.spider.bussiness.entity.NgramAnalyzerInfo;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractEClient extends CheckedUtil {

    protected static String join_field = "join_field";

    protected <T extends BaseEntity> void addDocBase(IndexRequest indexRequest, List<Field> reflectFields, T data) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();

        builder.startObject();
        reflectFields.forEach(acceptOrThrow(field -> {
                    field.setAccessible(Boolean.TRUE);
                    builder.field(field.getName(), field.get(data).toString());
                }
        ));
        builder.endObject();

        indexRequest.id(data.getId()).source(builder);
    }

    protected <U extends BaseEntity, R extends DocWriteResponse> void addDocExecute(String indexName, U data, BiCheckedFunction<String, U, R> biCheckedFunction){
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

    protected  <T> void addRelationDocBase(IndexRequest indexRequest, List<Field> reflectFields, T data, String objName, Boolean isChild) throws IOException {
        final String[] keyAndForeignKey = {"", ""};

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

        indexRequest.id(keyAndForeignKey[0]).source(builder);
        if(isChild) indexRequest.routing(keyAndForeignKey[1]);
    }

    protected static List<Field> getReflectFileds(Class clazz){
        return clazz != null ? Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()) : Collections.emptyList();
    }

    protected List<Field> filterField(List<Field> fieldList, Boolean isFilterChildAnno){
        return fieldList.stream().filter(f ->
                isFilterChildAnno ^ f.isAnnotationPresent(ChildAnnotation.class)
        ).collect(Collectors.toList());
    }

    protected List<Field> filterCreateIndexField(List<Field> fieldList, Boolean isFilterChildAnno){
        return fieldList.stream().filter(f ->
                (isFilterChildAnno ^ f.isAnnotationPresent(ChildAnnotation.class)) && !f.isAnnotationPresent(ForeignKeyAnnotation.class)
        ).collect(Collectors.toList());
    }

    protected void buildRelationship(XContentBuilder builder, Map<String, String[]> relationshipMap) throws IOException {
        builder.startObject(join_field); // 构建关系
        builder.field("type", "join");
        builder.startObject("relations");
        relationshipMap.forEach(biAcceptOrThrow((parentName, childNameArr) -> {
            builder.array(parentName, childNameArr);
        }));
        builder.endObject();
        builder.endObject();
    }

    protected void buildChildObjField(XContentBuilder builder, List<Field> childAnnoFieldList, String parentName, Map<String, String[]> relationshipMap){
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

    protected void buildObjField(XContentBuilder builder, String objName, List<Field> fieldList) throws IOException {
        fieldList.forEach(acceptOrThrow(field ->
                buildEachFieldType(builder, field))
        );
    }

    protected void buildEachFieldType(XContentBuilder builder, Field field) throws IOException {
        builder.startObject(field.getName());
        builder.field("type", "text");
        if(field.isAnnotationPresent(AnalyzerAnnotation.class))
            builder.field("analyzer", field.getAnnotation(AnalyzerAnnotation.class).analyzerName().toString());
        builder.endObject();
    }

    protected void createNgramInfo(CreateIndexRequest createIndexRequest, List<NgramAnalyzerInfo> ngramAnalyzerInfoList) throws IOException {
        if(ngramAnalyzerInfoList != null && ngramAnalyzerInfoList.size() > 0){

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("analysis");

                builder.startObject("analyzer");
                    ngramAnalyzerInfoList.stream().forEach(acceptOrThrow(ngramAnalyzerInfo -> {
                        builder.startObject(ngramAnalyzerInfo.getAnalyzerName().toString());
                            builder.field("tokenizer", ngramAnalyzerInfo.getTokenizerName());
                        builder.endObject();
                    }));
                builder.endObject();

                builder.startObject("tokenizer");
                    ngramAnalyzerInfoList.stream().forEach(acceptOrThrow(ngramAnalyzerInfo -> {
                        builder.startObject(ngramAnalyzerInfo.getTokenizerName());
                            builder.field("type", ngramAnalyzerInfo.getNgramType());
                            builder.field("min_gram", ngramAnalyzerInfo.getMinGram());
                            builder.field("max_gram", ngramAnalyzerInfo.getMaxGram());
                        builder.endObject();
                    }));
                builder.endObject();

            builder.endObject();
            builder.endObject();

            createIndexRequest.settings(builder);
        }
    }

//    protected List<Map<String, Object>> parseSearchResponse(SearchResponse response){
    protected List<SearchHit> parseSearchResponse(SearchResponse response){
//        List<Map<String, Object>> resultList = new ArrayList<>();
        List<SearchHit> resultList = new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
//            resultList.add(hit.getSourceAsMap());
            resultList.add(hit);
        }
        return resultList;
    }
}
