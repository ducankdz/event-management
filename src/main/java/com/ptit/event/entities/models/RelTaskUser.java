package com.ptit.event.entities.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "rel_task_user")
@NoArgsConstructor
public class RelTaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    @ManyToOne()
    private Task task;

    @Column(name = "task_id", insertable = false, updatable = false)
    private Long taskId;

    @Column(name = "user_id")
    private Long userId;

    public RelTaskUser(Task task, Long userId) {
        this.task = task;
        this.userId = userId;
    }
}
