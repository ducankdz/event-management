package com.ptit.event.services.notification;

import com.ptit.event.entities.enums.Action;
import com.ptit.event.entities.enums.NotificationCode;
import com.ptit.event.entities.enums.NotificationType;
import com.ptit.event.entities.models.Notification;
import com.ptit.event.entities.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
  Page<Notification> filter(Long userId, List<UUID> keyword, Pageable pageable);

  Notification create(
      Long userId, Action action, NotificationType type, NotificationCode code, Object data);

  void createBatchByUsers(
      List<User> users, Action action, NotificationType type, NotificationCode code, Object data);

  void read(List<UUID> notiIds);
}
