package com.studyhub.notification.api.open;

import com.studyhub.notification.domain.entity.Notification;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    //알림 생성
    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.create(notification));
    }

    //유저별 알림 조회
    @GetMapping
    public ResponseEntity<List<Notification>> findByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }

    //단건 읽음 처리
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    //알림 삭제(단건)
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> delete(@PathVariable Long notificationId) {
        notificationService.delete(notificationId);
        return ResponseEntity.ok().build();
    }

    //알림 삭제(다중)
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        notificationService.deleteAll(ids);
        return ResponseEntity.ok().build();
    }
}
