package top.oldmoon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.oldmoon.esUtils.annotation.ElasticSField;
import top.oldmoon.esUtils.annotation.ElasticSIndex;
import top.oldmoon.esUtils.entity.base.QueryElastic;

import static top.oldmoon.esUtils.constant.SearchRuleEnum.*;

@Data
@NoArgsConstructor
@ElasticSIndex(index = "test", type = "_doc")
public class TestQuery extends QueryElastic {
    @ElasticSField(rule = EQUAL)
    public String server;
    @ElasticSField
    public String name;
    @ElasticSField(field = "nowDateTime", rule = GREATER_E)
    public String beginDateTime;
    @ElasticSField(field = "nowDateTime", rule = LESS_E)
    public String endDateTime;
}
