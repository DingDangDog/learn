package top.oldmoon.constant;

/**
 * 查询类型枚举类，各种查询规则
 *   后期可以考虑增加包含于和不包含于，
 *   且可以考虑与等于和不等于整合
 */
public enum SearchRuleEnum {
    FUZZY, // 模糊查询
    EQUAL, // 等于
    NOTEQUAL, // 不等于
    GREATER_E, // 大于等于
    LESS_E,  // 小于等于
    GREATER, // 大于
    LESS,  // 小于
    SORT // 排序
}
