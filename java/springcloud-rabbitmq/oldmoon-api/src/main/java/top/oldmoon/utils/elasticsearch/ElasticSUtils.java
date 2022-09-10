package top.oldmoon.utils.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.oldmoon.annotation.ElasticSField;
import top.oldmoon.annotation.ElasticSIndex;
import top.oldmoon.constant.SearchRuleEnum;
import top.oldmoon.entity.ElasticSCriteria;
import top.oldmoon.entity.base.QueryElastic;
import top.oldmoon.utils.RedisUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description ElasticSearch 操作工具类简单封装
 * @author oldmoon
 */
//@Component
public class ElasticSUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    RestHighLevelClient elacticSClient;

    /**
     * @description 保存数据到Elasticsearch
     * @param type   数据类型
     * @param index  索引（表名）
     * @param sourceJson  数据——json字符串
     * @return boolean  保存成功为true，失败为false
     * @throws IOException
     */
    public boolean insert(String type, String index, String sourceJson) throws IOException {

        // 创建请求对象
        IndexRequest request = new IndexRequest(index);
        // request.opType(type);// INDEX(0), CREATE(1), UPDATE(2), DELETE(3);
        request.type(type);
        request.source(sourceJson, XContentType.JSON);
        // 执行请求（保存数据），并获取响应对象
        IndexResponse response = elacticSClient.index(request, RequestOptions.DEFAULT);

        // 判断响应结果，没有id视为失败，返回false
        if (StringUtils.isEmpty(response.getId()))
            return false;

        return true;
    }

    public boolean insert(Object object) throws IOException {

        ElasticSIndex elasticSIndex = object.getClass().getAnnotation(ElasticSIndex.class); // 获取类注解
        String index = elasticSIndex.index(); // 获取索引
        String type = elasticSIndex.type(); // 获取类型

        // 实体类转json串
        String jsonString = JSON.toJSONString(object);
        // 创建请求对象
        IndexRequest request = new IndexRequest(index);
        // request.opType(type);// INDEX(0), CREATE(1), UPDATE(2), DELETE(3);
        request.type(type);
        request.source(jsonString, XContentType.JSON);
        // 执行请求（保存数据），并获取响应对象
        IndexResponse response = elacticSClient.index(request, RequestOptions.DEFAULT);

        // 判断响应结果，没有id视为失败，返回false
        if (StringUtils.isEmpty(response.getId()))
            return false;

        return true;
    }

    public String search(QueryElastic queryElastic) throws IOException {
        ElasticSIndex elasticSIndex = getElasticSIndex(queryElastic);
        List<ElasticSCriteria> criteriaList = null;
        try {
            criteriaList = setCriteria(queryElastic); // 设置查询条件集合
        } catch (IllegalAccessException e) {
            logger.error("封装查询条件集合出错：");
            e.printStackTrace();
        }
        // 查询请求对象
        SearchRequest searchRequest = new SearchRequest(elasticSIndex.index());
        // 查询条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 组合查询条件容器
        BoolQueryBuilder boolbuilder = new BoolQueryBuilder();

        StringBuilder objJson = new StringBuilder("[");

        // 设置查询数据起始索引及大小
        int size = (queryElastic.getPageSize() == null ? elasticSIndex.pageSize() : queryElastic.getPageSize());
        int from = (((queryElastic.getPageNum() == null ? elasticSIndex.pageNum() : queryElastic.getPageNum()) - 1) * size);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        // 遍历查询条件集合，设置查询条件
        criteriaList.forEach(criteria -> {
            QueryBuilder queryBuilder = setQueryBuilder(criteria);
            if ("or".equals(criteria.relation())){
                boolbuilder.should(queryBuilder); // 或：or---- TODO 按照我的考虑，“或”查询应该是有问题，有空可以测试下
            } else {
                boolbuilder.must(queryBuilder); // 默认：且：and
            }
            // 判断为排序条件，设置排序
            if(criteria.rule().equals(SearchRuleEnum.SORT)){
                if("asc".equals(criteria.sort())){
                    searchSourceBuilder.sort(criteria.field(), SortOrder.ASC);// 正序
                }else{
                    searchSourceBuilder.sort(criteria.field(), SortOrder.DESC);// 默认倒序
                }
            }
        });
        // 设置查询条件
        searchSourceBuilder.query(boolbuilder);
        searchRequest.source(searchSourceBuilder);
        // 执行查询
        SearchResponse searchResponse = elacticSClient.search(searchRequest, RequestOptions.DEFAULT);
        // 获取查询结果集
        SearchHits hits = searchResponse.getHits();

        // 结果处理：TODO 待优化，初步思路封装为分页对象，包含：总数、页码、当前数量、结果集合
        TotalHits total = hits.getTotalHits();
        SearchHit[] hitsArr = hits.getHits();
        if( total.value > 0 ){
            int num = 0;
            for (SearchHit hit : hitsArr) {
                num++;
                objJson.append(hit.getSourceAsString());
                if(hitsArr.length != num){
                    objJson.append(",");
                }
            }
        }
        objJson.append("]");

        return objJson.toString();

    }

    /**
     * @description 设置查询条件
     * @param criteria 查询条件封装实体
     */
    private QueryBuilder setQueryBuilder(ElasticSCriteria criteria){
        QueryBuilder queryBuilder = null;
        List<SearchRuleEnum> rules = criteria.rule();
        int i = 0;
        for (SearchRuleEnum rule: rules){
            i++;
            sw: switch (rule){
                case FUZZY: // 模糊查询
                    queryBuilder = QueryBuilders.wildcardQuery(criteria.field(),"*" + criteria.info() + "*");
                    break sw;
                case EQUAL: // 等于
                    // id只支持全等匹配
                    if ("id".equals(criteria.field())) {
                        queryBuilder = QueryBuilders.idsQuery().addIds(criteria.field());
                        break sw;
                    }
                    queryBuilder = QueryBuilders.termsQuery(criteria.field(), criteria.info());
                    break sw;
                case NOTEQUAL: // 不等于
                    queryBuilder = QueryBuilders.termsQuery(criteria.field(), criteria.info());
                    break sw;
                case SORT:
                    break sw;
                case GREATER: // 大于
                    if(i == 1) {
                        queryBuilder = QueryBuilders.rangeQuery(criteria.field()).gt(criteria.begin());
                    } else {
                        ((RangeQueryBuilder)queryBuilder).gt(criteria.begin());
                    }
                    break sw;
                case LESS: // 小于
                    if(i == 1) {
                        queryBuilder = QueryBuilders.rangeQuery(criteria.field()).lt(criteria.end());
                    } else {
                        ((RangeQueryBuilder)queryBuilder).lt(criteria.end());
                    }
                    break sw;
                case GREATER_E: // 大于等于
                    if(i == 1) {
                        queryBuilder = QueryBuilders.rangeQuery(criteria.field()).gte(criteria.begin());
                    } else {
                        ((RangeQueryBuilder)queryBuilder).gte(criteria.begin());
                    }
                    break sw;
                case LESS_E: // 小于等于
                    if(i == 1) {
                        queryBuilder = QueryBuilders.rangeQuery(criteria.field()).lte(criteria.end());
                    } else {
                        ((RangeQueryBuilder)queryBuilder).lte(criteria.end());
                    }
                    break sw ;
            }
        }

        return queryBuilder;
    }

    /**
     * @description 封装查询条件
     * @param queryElastic 查询条件实体
     */
    private List<ElasticSCriteria> setCriteria(QueryElastic queryElastic) throws IllegalAccessException {
        List<ElasticSCriteria> criteriaList = new ArrayList<ElasticSCriteria>();

        Field[] Fields = queryElastic.getClass().getDeclaredFields(); // 获取全部属性
        for (Field field : Fields) {
            ElasticSCriteria criteria = new ElasticSCriteria();
            if(!field.isAccessible()) field.setAccessible(true); // 设置属性可操作性

            ElasticSField elasticSField = field.getAnnotation(ElasticSField.class); // 获取属性注解
            if (elasticSField == null) continue; // 如果没用注解，跳过该属性
            // 判断字段名是否手动设置
            if(StringUtils.isEmpty(elasticSField.field())){
                criteria.field(field.getName());
            } else {
                criteria.field(elasticSField.field());
            }
            Object info = field.get(queryElastic);
            if(info == null){
                continue;
            }
            criteria.info(info)
                    .rule(new ArrayList(
                            Arrays.asList(new SearchRuleEnum[]{
                                    elasticSField.rule()
                            })))
                    .sort(elasticSField.sort())
                    .relation(elasticSField.relation());

            sw: switch (elasticSField.rule()) {
                case GREATER: // 大于和大于等于
                case GREATER_E:
                    for (ElasticSCriteria elasticSCriteria : criteriaList) {
                        if (elasticSCriteria.field().equals(elasticSField.field())) {
                            elasticSCriteria.rule().add(elasticSField.rule());
                            elasticSCriteria.begin(info);
                            break sw;
                        }
                    }

                    criteria.begin(info);
                    criteriaList.add(criteria);
                    break sw;
                case LESS: // 小于和小于等于
                case LESS_E:
                    for (ElasticSCriteria elasticSCriteria : criteriaList) {
                        if (elasticSCriteria.field().equals(elasticSField.field())) {
                            elasticSCriteria.rule().add(elasticSField.rule());
                            elasticSCriteria.end(info);
                            break sw;
                        }
                    }
                    criteria.end(info);
                    criteriaList.add(criteria);
                    break sw;
                default:
                    criteriaList.add(criteria);
                    break sw;
            }
//            criteriaList.add(criteria);
            field.setAccessible(false); // 设置属性可操作性
        }

        return criteriaList;
    }

    /**
     * @description 获取实体类上的ElasticSIndex注解
     * @param queryElastic 实体类
     */
    private ElasticSIndex getElasticSIndex(QueryElastic queryElastic){
        return queryElastic.getClass().getAnnotation(ElasticSIndex.class); // 获取类注解
    }
}
