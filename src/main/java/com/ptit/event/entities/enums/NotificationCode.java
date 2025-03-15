package com.ptit.event.entities.enums;

public enum NotificationCode {
  ;

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
