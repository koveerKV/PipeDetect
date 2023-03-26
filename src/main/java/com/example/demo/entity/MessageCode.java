package com.example.demo.entity;

public enum MessageCode {
    SUCCESS(200, "成功", true),
    PAGE_NOT_FOUND(404, "页面不存在", false),
    ERROR(400, "请求失败，请联系管理员", false);
    private final Integer code;
    private final String message;
    private final boolean success;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    MessageCode(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
