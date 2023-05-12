package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * null.
 *
 * @author koveer
 * @since 1.0
 * - 2023/5/5 21:20
 */
@Data
@AllArgsConstructor
@ToString
public class Name {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String pName;
}
