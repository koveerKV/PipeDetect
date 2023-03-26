package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@ApiModel
//检测任务
public class Task {
    @TableId(type = IdType.AUTO)
    private Integer id;//检测任务的批次
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//检测任务开始时间
    private String batch;
}
