package com.se.domain;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 15:32 2020/5/24
 * @Modified by:
 */

import java.util.Date;

/**
 * @Classname: Area
 * @Description: 实体类-区域
 * @Author: Qi weidong
 * @Date: 2020/5/24 15:32
 */

/**
 * 涉及知识点，返回值类型设置为引用类型，为了防止默认值为0，当为空就是null
 */
public class Area {
    //id
    private Integer areaId;
    //名称
    private String areaName;
    //权重
    private String areaDesc;
    private Integer priority;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;

    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", areaDesc='" + areaDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
