package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回最新一次任务的缺陷信息.
 *
 * @author koveer
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTask {
    private Integer id;
    private Integer pid;
    private Integer def1;//缺陷类型
    private Integer def2;//缺陷类型
    private Integer def3;//缺陷类型
    private Integer def4;//缺陷类型
}
