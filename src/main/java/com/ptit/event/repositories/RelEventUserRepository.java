package com.ptit.event.repositories;

import com.ptit.event.entities.models.RelEventUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelEventUserRepository extends JpaRepository<RelEventUser, Long> {

  @Query(nativeQuery = true, value = "select count(id) from rel_event_user where event_id = :id")
  Long countGuestInEvent(Long id);

  List<RelEventUser> findByEventId(Long id, Pageable pageable);
}
