package com.studyhub.notification.service;

import com.studyhub.notification.domain.dto.NotificationRequestDto;
import com.studyhub.notification.domain.dto.NotificationResponseDto;
import com.studyhub.notification.domain.entity.Notification;
import com.studyhub.notification.domain.repository.NotificationRepository;
import com.studyhub.notification.event.message.chat.ChatEvent;
import com.studyhub.notification.event.message.study.StudyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponseDto create(NotificationRequestDto dto) {
        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .type(dto.getType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .externalId(dto.getExternalId())
                .isRead(false)
                .build();
        notification.initializeTimestamps();
        return NotificationResponseDto.from(notificationRepository.save(notification));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationResponseDto findById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));
        return NotificationResponseDto.from(notification);
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> findByFilter(Long userId, Boolean isRead, String type, Pageable pageable) {
        return notificationRepository.findByUserIdAndOptionalFilters(userId, isRead, type, pageable)
                .map(NotificationResponseDto::from);
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids) {
        notificationRepository.deleteAllById(ids);
    }


    public void handleStudyEvent(StudyEvent event) {
        if (notificationRepository.existsByExternalId(event.getExternalId())) {
            return;
        }

        Notification notification = Notification.of(event);
        notificationRepository.save(notification);
    }

    public void handleUserMessage(ChatEvent event) {
        if (notificationRepository.existsByExternalId(event.getExternalId())) return;
        Notification notification = Notification.of(event);
        notificationRepository.save(notification);
    }

//    public void handleUserReply(UserReplyEvent event) {
//        if (notificationRepository.existsByExternalId(event.getExternalId())) return;
//        Notification notification = Notification.of(event);
//        notificationRepository.save(notification);
//    }
}


