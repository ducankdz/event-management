package com.ptit.event.repositories;

import com.ptit.event.entities.models.RelEventUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelEventUserRepository extends JpaRepository<RelEventUser, Long> {
}
