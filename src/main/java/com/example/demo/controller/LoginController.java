package com.example.demo.controller;

import com.example.demo.entity.Batch;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageCode;
import com.example.demo.mapper.BatchMapper;
import com.example.demo.service.AutoService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:9528")
@RequiredArgsConstructor
public class LoginController {
    final AutoService autoService;
    final BatchMapper batchMapper;

    @PostMapping("/api/login")
    @ResponseBody
    public Message login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            //1.构造登录令牌 UsernamePasswordToken
            //加密密码
//            password = new Md5Hash(password, username, 3).toString();  //1.密码，盐，加密次数
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(upToken);
            String sessionId = (String) subject.getSession().getId();
            if (batchMapper.selectById(1) == null)
                batchMapper.insert(new Batch(null, "unknow"));
            autoService.autoRun();
            return new Message(MessageCode.SUCCESS, sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(401, "用户不存在", null);
        }
    }

    @RequestMapping("/api/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:http://localhost:9528/#/login";
    }
}
