package top.oldmoon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.oldmoon.constant.SearchRuleEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description Elasticsearch查询条件封装类
 * @author oldmoon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true) // 开启链式方法
public class ElasticSCriteria {

    /**
     * 条件字段
     */
    private String field;
    /**
     * 条件规则 SearchRuleEnum ，默认值：模糊
     * 声明为数组的原因：当要根据字段范围查询时，如时间范围，此字段会包含 大于/大于等于 和 小于/小于等于 两种规则
     */
    private List<SearchRuleEnum> rule = new ArrayList(Arrays.asList(new SearchRuleEnum[]{SearchRuleEnum.FUZZY}));
    /**
     * 条件内容：：rule == SearchRuleEnum.EQUAL 取该字段内容
     */
    private Object info;
    /**
     * 条件开始：：rule == SearchRuleEnum.GREATER || GREATER_E 取该字段内容
     */
    private Object begin;
    /**
     * 条件结束：：rule == SearchRuleEnum.LESS || LESS_E 取该字段内容
     */
    private Object end;
    /**
     * 条件关系：：and  or
     */
    private String relation;
    /**
     * 排序规则 asc 正序； desc 倒序，rule为SORT时用到该值
     */
    private String sort;

}
