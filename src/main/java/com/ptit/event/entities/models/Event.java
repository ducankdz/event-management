package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.values.Location;
import com.ptit.event.entities.values.Schedule;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
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
    @Column(name = "owner_id", updatable = false, insertable = false)
    private User owner;

    @Column(name = "updated_user_id")
    private Long updatedUserId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images", columnDefinition = "json")
    private List<String> images;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EventState state;

    @JdbcTypeCode(SqlTypes.JAVA_OBJECT)
    @Column(name = "location")
    private Location location;

    @JdbcTypeCode(SqlTypes.JAVA_OBJECT)
    @Column(name = "schedule")
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
