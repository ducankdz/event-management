package com.ptit.event.repositories;

import com.ptit.event.entities.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

  @Modifying
  @Query(nativeQuery = false, value = "update Notification set read = true where id in :ids")
  void markIsRead(List<UUID> ids);

  @Query(
      nativeQuery = false,
      value =
          "select n from Notification n where n.userId = :userId and "
              + ":keyword is null or n.code in :keyword")
  Page<Notification> filter(Long userId, List<UUID> keyword, Pageable pageable);
}
