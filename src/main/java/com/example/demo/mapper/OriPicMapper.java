package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.OriPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OriPicMapper extends BaseMapper<OriPic> {
    @Select("SELECT * FROM pic.ori_pic")
    OriPic[] selectAll();
}
