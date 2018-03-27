package com.article.recommend.entity;

/**
 * 数据记录
 */
public class ExecuteDbRecord {
    private Long id;
    private String executeType;
    private Long beforeId;
    private String updateTime;
    private String createTime;
    private Integer rownums;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }



    public Long getBeforeId() {
        return beforeId;
    }

    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRownums() {
        return rownums;
    }

    public void setRownums(Integer rownums) {
        this.rownums = rownums;
    }
}
