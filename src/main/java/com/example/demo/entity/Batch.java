package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * null
 *
 * @author koveer
 * @since 2.0
 * - 2023/3/23 20:37
 */
@Data
@AllArgsConstructor
public class Batch {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
}
