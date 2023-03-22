package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单次任务信息全部录入完成更新此字段.
 *
 * @author koveer
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class Msg {
    @TableId
    private Integer id;
    private Integer isTrue;

    public Msg(Integer id, Integer isTrue) {
        this.id = id;
        this.isTrue = isTrue;
    }
}
