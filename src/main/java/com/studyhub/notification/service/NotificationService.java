package com.studyhub.notification.service;

import com.studyhub.notification.domain.dto.NotificationEventDto;
import com.studyhub.notification.domain.dto.NotificationRequestDto;
import com.studyhub.notification.domain.dto.NotificationResponseDto;
import com.studyhub.notification.domain.entity.Notification;
import com.studyhub.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // Kafka 수신용
    public void save(NotificationEventDto dto) {
        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .type(dto.getType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .link(dto.getLink())
                .isRead(false)
                .build();
        notification.initializeTimestamps();
        notificationRepository.save(notification);
    }

    // 알림 생성 (RequestDto → Entity → 저장 → ResponseDto 반환)
    public NotificationResponseDto create(NotificationRequestDto dto) {
        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .type(dto.getType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .link(dto.getLink())
                .isRead(false)
                .build();
        notification.initializeTimestamps();
        return NotificationResponseDto.from(notificationRepository.save(notification));
    }

    // 유저별 알림 전체 조회
    public List<NotificationResponseDto> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public NotificationResponseDto findById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));
        return NotificationResponseDto.from(notification);
    }

    // 읽음 처리
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));
        notification.markAsRead();
        notificationRepository.save(notification);
    }

    // 삭제 (단건)
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    // 삭제 (여러 개)
    public void deleteAll(List<Long> ids) {
        notificationRepository.deleteAllById(ids);
    }
}
