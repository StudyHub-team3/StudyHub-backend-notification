package com.studyhub.notification.service;

import com.studyhub.notification.domain.entity.Notification;
import com.studyhub.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

     //알림 생성
    public Notification create(Notification notification) {
        notification.initializeTimestamps();
        return notificationRepository.save(notification);
    }

     //사용자 ID 기준 알림 전체 조회
    public List<Notification> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

     //알림 단건 읽음 처리
    public void markAsRead(Long id) {
        Notification noti = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));
        noti.markAsRead();
        notificationRepository.save(noti);
    }

     //알림 단건 삭제
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    //알림 여러 개 삭제 (일괄 삭제)
    public void deleteAll(List<Long> ids) {
        notificationRepository.deleteAllById(ids);
    }


     //알림 단건 조회
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));
    }
}
