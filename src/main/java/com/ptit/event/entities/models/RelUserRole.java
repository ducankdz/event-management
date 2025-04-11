package com.ptit.event.entities.models;

import com.ptit.event.entities.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rel_user_role")
@Data
public class RelUserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long objectId;

  private Long roleId;

  @ManyToOne private Role role;

  private Type type;
}
