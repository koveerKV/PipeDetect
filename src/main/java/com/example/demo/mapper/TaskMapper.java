package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.NewTask;
import com.example.demo.entity.Result;
import com.example.demo.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 通过时间分类获取检测结果
     */
    @Select("SELECT r.* FROM pic.result r JOIN (SELECT id FROM pic.task WHERE create_time BETWEEN #{beginDate} AND #{endDate}) t WHERE t.id = r.task_id")
    Result[] selectResultAllByTaskDate(Date beginDate, Date endDate);

//    /**
//     * 通过日期获取任务列表
//     */
//    @Select("SELECT id FROM pic.task WHERE create_time BETWEEN #{date} AND DATE_ADD(#{date}, INTERVAL 1 DAY)")
//    public Result[] selectAllByDate(Date date);

    @Select("SELECT r.def1,r.def2,r.def3,r.def4,s2.* FROM pic.result r JOIN (SELECT m.mark_id,s1.id FROM pic.mark_pic m JOIN (SELECT id FROM pic.task ORDER BY id DESC LIMIT 1) s1 ON m.task_id = s1.id) s2 ON r.mark_id = s2.mark_id")
    @org.apache.ibatis.annotations.Result(property = "pid", column = "mark_id")
    NewTask[] getNew();

    @Select("SELECT * FROM pic.task")
    Task[] selectAll();

    @Select("SELECT COUNT(id) FROM pic.task")
    int countById();

}
