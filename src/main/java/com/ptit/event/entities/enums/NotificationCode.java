package com.ptit.event.entities.enums;

public enum NotificationCode {
  UPDATE_EVENT(
      "[Sự kiện] Cập nhật sự kiện", "[Sự kiện] Sự kiện #name# vừa được cập nhật bởi #user_name#"),
  CREATE_EVENT("[Sự kiện] Tạo mới sự kiện", ""),
  DELETE_EVENT("[Sự kiện] Xóa sự kiện", "[Sự kiện] Sự kiện #name# vừa xóa");

  private final String title;
  private final String content;

  NotificationCode(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
