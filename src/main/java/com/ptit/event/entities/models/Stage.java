package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.State;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "stage")
public class Stage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "evnt_id")
  private Long eventId;

  @ManyToOne
  @Column(name = "event_id", insertable = false, updatable = false)
  private Event event;

  @Column(name = "created_user_id")
  private Long createdUserId;

  @Column(name = "updated_user_id")
  private Long updatedUserId;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "stage")
  private State stage;

  @Column(name = "description")
  private String description;

  @Column(name = "ended_At")
  private Timestamp endedAt;

  @Column(name = "started_At")
  private Timestamp startedAt;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @CreationTimestamp
  @Column(name = "created_At")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_At")
  private Timestamp updatedAt;
}
