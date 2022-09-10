package top.oldmoon.entity.base;

import java.io.Serializable;


/**
 * @description Elasticsearch查询基类
 * @author oldmoon
 */
public class QueryElastic implements Serializable {
    private Integer pageNum; // 页码
    private Integer pageSize; // 页面大小

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
