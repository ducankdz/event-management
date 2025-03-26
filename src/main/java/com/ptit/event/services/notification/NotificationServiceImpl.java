package com.ptit.event.services.notification;

import com.ptit.event.entities.enums.Action;
import com.ptit.event.entities.enums.NotificationCode;
import com.ptit.event.entities.enums.NotificationType;
import com.ptit.event.entities.models.Notification;
import com.ptit.event.entities.models.RelEventUser;
import com.ptit.event.entities.models.User;
import com.ptit.event.repositories.NotificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ptit.event.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  @Autowired private NotificationRepository repository;

  @Autowired private UserRepository userRepository;

  @Override
  public Page<Notification> filter(Long userId, List<UUID> keyword, Pageable pageable) {
    return repository.filter(userId, keyword, pageable);
  }

  @Async
  @Transactional
  @Override
  public Notification create(
      Long userId, Action action, NotificationType type, NotificationCode code, Object data) {
    Notification notification =
        Notification.builder()
            .userId(userId)
            .action(action)
            .code(code)
            .type(type)
            .title(code.getTitle())
            .content(code.getContent())
            .data(data)
            .build();
    return repository.save(notification);
  }

  @Async
  @Transactional
  @Override
  public void createBatchByUsers(
      List<User> users, Action action, NotificationType type, NotificationCode code, Object data) {
    for (User user : users) {
      Map<String, Object> dataMap = (Map<String, Object>) data;
      dataMap.put("user_name", user.getFullName());
      dataMap.put("avatar", user.getAvatar());
      create(user.getId(), action, type, code, user);
    }
  }

  @Transactional
  @Override
  public void read(List<UUID> notiIds) {
    repository.markIsRead(notiIds);
  }
}
