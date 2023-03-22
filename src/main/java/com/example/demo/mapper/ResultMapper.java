package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResultMapper extends BaseMapper<Result> {

    @Select("")
    Result[] selectAllByResDescription(List<String> list);

    @Select("SELECT * FROM pic.result")
    Result[] selectAll();

    @Select("SELECT COUNT(res_id) FROM pic.result")
    int countByResId();

    @Select("SELECT COUNT(def1) FROM pic.result WHERE def1 != 0")
    int countByDef1();

    @Select("SELECT COUNT(def2) FROM pic.result WHERE def2 != 0")
    int countByDef2();

    @Select("SELECT COUNT(def3) FROM pic.result WHERE def3 != 0")
    int countByDef3();

    @Select("SELECT COUNT(def4) FROM pic.result WHERE def4 != 0")
    int countByDef4();

    @Select("SELECT COUNT(res_id) FROM pic.result WHERE def1 = 0 AND def2 = 0 AND def3 = 0 AND def4 = 0")
    int countByDef0();
}
