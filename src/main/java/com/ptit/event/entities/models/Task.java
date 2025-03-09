package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.State;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "task")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_user_id")
  private Long createdUserId;

  @Column(name = "updated_user_id")
  private Long updatedUserId;

  @Column(name = "event_id")
  private Long eventId;

  @Column(name = "stage_id")
  private Long stageId;

  @ManyToOne
  @Column(name = "stage_id")
  private Stage stage;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private State state;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "images")
  private List<String> images;

  @Column(name = "budget")
  private Long budget;

  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RelTaskUser> relTaskUsers = new ArrayList<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
