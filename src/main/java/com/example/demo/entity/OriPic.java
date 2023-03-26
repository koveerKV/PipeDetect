package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
//原始图片
public class OriPic {
    @TableId
    private Integer oriId;//原始图片id
    private Integer taskId;//对应的任务批次
    private Integer taskNumber;//该批次中，图片序号
    private String oriPic;//原始图片
}
