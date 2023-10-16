//package com.example.demo.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.demo.entity.Message;
//import com.example.demo.entity.MessageCode;
//import com.example.demo.entity.Msg;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * websocket服务
// *
// * @author koveer
// * @since 3.0
// * - 2023/3/39 12:42
// */
//@Component
//@Slf4j
//@ServerEndpoint("/api/websocket/{id}") //暴露的ws应用的路径
//@CrossOrigin(origins = "http://localhost:9528")
//public class WebSocketServer {
//
//    // 用来存储服务连接对象，根据id获取对应的Session
//    private static Map<String, Session> clientMap = new ConcurrentHashMap<>();
//
//    public Session getSession(String id){
//        return clientMap.get(id);
//    }
//
//    /**
//     * 访问@ServerEndpoint注解中的地址时启动.
//     *
//     * @param id 用户id，由于写在路径中，需要加密，本项目仅运行在内网的一台实体机上，所以不作加密
//     * @param session 用户会话
//     * @author koveer
//     * -2023/3/27 22:07
//     * @since 3.0
//     */
//    @OnOpen
//    public void onOpen(@PathParam("id") String id, Session session) {
//        clientMap.put(id, session);
//        System.out.println(id);
//    }
//
//    /**
//     * 当WebSocket连接关闭时，该方法被调用。在该方法中，您可以执行一些清理任务，如将会话从会话列表中删除等.
//     *
//     * @param id 用户id
//     * @param session 用户会话
//     * @author koveer
//     * -2023/3/27 22:06
//     * @since 3.0
//     */
//    @OnClose
//    public void onClose(@PathParam("id")String id, Session session) {
//        clientMap.remove(id);
//    }
//
//    /**
//     * 当WebSocket发生错误时，该方法被调用。在该方法中，您可以记录错误信息，或在必要时向客户端发送错误消息.
//     *
//     * @param error 错误
//     * @param session 用户会话
//     * @author koveer
//     * -2023/3/27 22:06
//     * @since 3.0
//     */
//    @OnError
//    public void onError(Throwable error, Session session) {
//        error.printStackTrace();
//    }
//
//    /**
//     * 当服务器收到客户端发送的消息时自动调用，但可以通过保存WebSocket的Session会话调用其发送消息的方法.
//     *
//     * @param session 用户会话
//     * @param message 发送的消息
//     * @author koveer
//     * -2023/3/27 22:15
//     * @since 3.0
//     */
//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException {
//        for (Session s : session.getOpenSessions()) {
//            s.getBasicRemote().sendText(message);
//        }
//
//    }
//    /**
//     * 向id传递msg信息.
//     *
//     * @param id 用户id
//     * @param msg 传递的信息
//     * @author koveer
//     * -2023/3/31 13:20
//     * @since 1.0
//     */
//    public void sendMessage(String id, Msg msg){
//        String s = JSONObject.toJSONString(new Message(MessageCode.SUCCESS,msg));
//        try {
//            this.getSession("admin").getBasicRemote().sendText(s);
//            System.out.println(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
