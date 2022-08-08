package top.oldmoon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 规定当前类对应Elasticsearch的索引及其类型
 * @author oldmoon
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSIndex {
    public String index();
    public String type();

    public int pageNum() default 1;
    public int pageSize() default 10;
}
