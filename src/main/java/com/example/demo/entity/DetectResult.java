package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 全部检测结果
 */
@Data
@NoArgsConstructor
public class DetectResult {
    private Integer pId;
    private Integer taskId;
    private Integer taskNumber;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer def1;//缺陷类型
    private Integer def2;//缺陷类型
    private Integer def3;//缺陷类型
    private Integer def4;//缺陷类型

    @Deprecated
    public String MytoString() {
        return "DetectResult{" +
                "pId=" + pId +
                ", taskId=" + taskId +
                ", createTime=" + createTime +
                '}';
    }
}
