package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Msg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface MsgMapper extends BaseMapper<Msg> {

    @Select("SELECT * from pic.msg order by id desc limit 1")
    Msg getNew();

    @Insert("INSERT INTO pic.msg values (#{id},#{istrue})")
    void Myinsert(Integer id, Integer istrue);
}
