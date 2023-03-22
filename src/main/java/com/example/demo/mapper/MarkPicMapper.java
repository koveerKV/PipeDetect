package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.MarkPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MarkPicMapper extends BaseMapper<MarkPic> {
    /**
     *
     */
    @Select("SELECT * FROM pic.mark_pic")
    MarkPic[] selectAll();
}
