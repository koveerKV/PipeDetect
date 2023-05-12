package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Name;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * null.
 *
 * @author koveer
 * @since 1.0
 * - 2023/5/5 21:21
 */
@Mapper
public interface NameMapper extends BaseMapper<Name> {
    @Select("SELECT n.p_name FROM pic.name n where n.id = (SELECT MAX(id) FROM pic.name) ")
    String getNew();

    @Select("SELECT * FROM pic.name")
    Name[] selectAll();
}
