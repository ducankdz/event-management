package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.values.Location;
import com.ptit.event.entities.values.Schedule;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "event")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "owner_id")
  private Long ownerId;

  @ManyToOne()
  @JoinColumn(name = "owner_id", updatable = false, insertable = false)
  private User owner;

  @Column(name = "updated_user_id")
  private Long updatedUserId;

  @Column(name = "name")
  private String name;

  @Column(name = "avatar")
  private String avatar;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "images", columnDefinition = "jsonb")
  private List<String> images;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private EventState state;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "location", columnDefinition = "jsonb")
  private Location location;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "schedule", columnDefinition = "jsonb")
  private Schedule schedule;

  @Column(name = "started_at")
  private Timestamp startedAt;

  @Column(name = "ended_at")
  private Timestamp endedAt;

  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
