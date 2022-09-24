package top.oldmoon.annotation;

import top.oldmoon.constant.SearchRuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用于自定义查询实体的查询条件上，规定当前条件详情（查询规则，组合方式，查询字段等）
 *
 * @author DDD
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSField {

    /**
     * 指定Elasticsearch中存储的字段名称，默认为空
     * 为空时直接取java实体类中的属性名作为字段名
     */
    String field() default "";

    /**
     * 查询规则（模糊查询，大于，小于等），默认模糊
     */
    SearchRuleEnum rule() default SearchRuleEnum.FUZZY;

    /**
     * 查询组合，且 或，默认且 and
     */
    String relation() default ElasticSField.AND;

    /**
     * 排序，默认倒序，只有rule指定为SORT时，才会用到该值
     */
    String sort() default ElasticSField.DESC;

    /**
     * 组合方式：且
     */
    String AND = "and";
    /**
     * 组合方式：或
     */
    String OR = "or";

    /**
     * 排序方式：正序
     */
    String ASC = "asc";
    /**
     * 排序方式：倒叙
     */
    String DESC = "desc";

}
