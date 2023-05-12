package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Batch;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageCode;
import com.example.demo.entity.Name;
import com.example.demo.mapper.BatchMapper;
import com.example.demo.mapper.NameMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 上传功能
 *
 * @author koveer
 * @since 2.0
 * - 2023/3/23 20:42
 */

@RestController
@Api
@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/api")
@RequiredArgsConstructor
//TODO
public class UpdateController {

    final BatchMapper batchMapper;
    final NameMapper nameMapper;

    /**
     * 上传名称.
     *
     * @param name 名称
     * @return java.lang.String
     * @author koveer
     * -2023/3/23 21:01
     * @since 2.0
     */
    @PostMapping("/name")
    public String updateName(@RequestParam("name") String name, @RequestParam("pname") String pName) {
//        String regex = "[\\w-]{1,30}";
//
//        //未输入或者输入不规范
//        if ((name == null || !name.matches(regex)) || ((pName == null || !pName.matches(regex)))) {
//            return JSONObject.toJSONString(new Message(400, "Bad Request",
//                    "输入格式有误，名称只能由长度1-20的'字母' '数字' '_' '-'组成 "));
//        }

        //输入合法
        String batch = batchMapper.getNew();
        if (!batch.equals(name))
            batchMapper.insert(new Batch(null, name));

        String temp = nameMapper.getNew();
        if (!temp.equals(pName))
            nameMapper.insert(new Name(null, pName));

        return JSONObject.toJSONString(new Message(MessageCode.SUCCESS, name));
    }
}
