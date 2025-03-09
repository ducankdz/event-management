package com.ptit.event.repositories;

import com.ptit.event.entities.models.RelTaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelTaskUserRepository extends JpaRepository<RelTaskUser, Long> {
}
