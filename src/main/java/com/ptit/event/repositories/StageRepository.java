package com.ptit.event.repositories;

import com.ptit.event.entities.models.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    Page<Stage> findByEventId(Long eventId, Pageable pageable);
}
