package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
//检测结果
public class Result {
    @TableId(type = IdType.AUTO)
    private Integer resId;//结果的id
    private Integer markId;//对应的检测后图片id
    private Integer taskId;//对应的任务批次
    private Integer def1;//缺陷类型
    private Integer def2;//缺陷类型
    private Integer def3;//缺陷类型
    private Integer def4;//缺陷类型
}
