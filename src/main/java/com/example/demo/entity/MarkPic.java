package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class MarkPic {
    @TableId(type = IdType.AUTO)
    private Integer markId;
    private Integer taskId;
    private Integer taskNumber;
    private byte[] markPic;
}
