package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.Action;
import com.ptit.event.entities.enums.NotificationCode;
import com.ptit.event.entities.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "notification")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
  @Id @GeneratedValue private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "action")
  private Action action;

  @Enumerated(EnumType.STRING)
  @Column(name = "code")
  private NotificationCode code;

  @Column(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "data")
  private Object data;

  @Column(name = "read")
  private final Boolean read = false;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
