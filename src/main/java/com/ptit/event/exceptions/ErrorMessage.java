package com.ptit.event.exceptions;

public enum ErrorMessage {
    EVENT_NOT_FOUND("Không tìm thấy sự kiện"),
    STAGE_NOT_FOUND("Không tìm thấy giai đoạn"), TASK_NOT_FOUND("Không tìm thấy công việc");


    private String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
