package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * null
 *
 * @author koveer
 * @since 1.0.0
 * - 2023/3/23 20:39
 */
@Mapper
public interface BatchMapper extends BaseMapper<Batch> {

    @Select("SELECT n.name FROM pic.batch n where n.id = (SELECT MAX(id) FROM pic.batch) ")
    String getNew();

    @Select("SELECT * FROM pic.batch")
    Batch[] selectAll();
}
