package com.ptit.event.responses;

import com.ptit.event.entities.models.RelUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelUserRolePermissonRepository extends JpaRepository<RelUserRole, Long> {

  @Query("select a.role.name from RelUserRole a where a.userId = :userId and a.eventId = :eventId")
  List<String> findPermissionsByUserIdAndEventId(Long userId, Long eventId);
}
