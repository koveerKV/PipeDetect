package com.example.demo.mapper;

import com.example.demo.entity.DetectResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DetectResultMapper {
    @Select("SELECT r.def1,r.def2,r.def3,r.def4,k1.* FROM pic.result r JOIN (SELECT t.*,o.ori_id,o.task_number FROM pic.task t JOIN pic.ori_pic o ON t.id = o.task_id) k1 ON k1.ori_id = r.mark_id;")
    @Result(property = "pId", column = "ori_id")
    @Result(property = "taskId", column = "id")
    DetectResult[] selectAllDetect();

    @Select("SELECT r.def1,r.def2,r.def3,r.def4,k1.* FROM pic.result r JOIN (SELECT t.*,o.ori_id,o.task_number FROM pic.task t JOIN pic.ori_pic o ON t.id = o.task_id WHERE t.id = #{taskid}) k1 ON k1.ori_id = r.mark_id;")
    @Result(property = "pId", column = "ori_id")
    @Result(property = "taskId", column = "id")
    DetectResult[] selectAllDetectByTaskId(Integer taskid);
}
