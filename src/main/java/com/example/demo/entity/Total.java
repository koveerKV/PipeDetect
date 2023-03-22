package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回数据库中各类型缺陷总计数.
 *
 * @author koveer
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Total {
    private Integer con;
    private Integer def0;
    private Integer def1;
    private Integer def2;
    private Integer def3;
    private Integer def4;
}
