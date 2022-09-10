package top.oldmoon.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.oldmoon.annotation.ElasticSField;
import top.oldmoon.annotation.ElasticSIndex;
import top.oldmoon.constant.SearchRuleEnum;
import top.oldmoon.entity.base.QueryElastic;

@Data
@NoArgsConstructor
@ElasticSIndex(index = "test", type="_doc")
public class TestQuery extends QueryElastic {
    @ElasticSField(rule = SearchRuleEnum.EQUAL)
    private String server;
    @ElasticSField
    private String name;

    @ElasticSField(field = "nowDateTime", rule = SearchRuleEnum.GREATER_E)
    private Long startDateTime;
    @ElasticSField(field = "nowDateTime", rule = SearchRuleEnum.LESS_E)
    private Long endDateTime;


}
