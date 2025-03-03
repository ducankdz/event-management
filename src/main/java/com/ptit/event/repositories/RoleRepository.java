package com.ptit.event.repositories;

import com.ptit.event.entities.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
