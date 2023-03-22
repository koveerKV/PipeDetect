package com.example.demo.entity;

import lombok.Data;

@Data
public class Message {
    private Integer code;
    private String message;
    private Object data;

    public Message(MessageCode messageCode, Object data) {
        this.code = messageCode.getCode();
        this.message = messageCode.getMessage();
        this.data = data;
    }

    public Message(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
