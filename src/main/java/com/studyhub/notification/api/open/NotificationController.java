package com.studyhub.notification.api.open;

import com.studyhub.notification.common.dto.ApiResponseDto;
import com.studyhub.notification.domain.entity.Notification;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class NotificationController {

    private final NotificationService notificationService;

    //[내부] Kafka 수신 후 알림 저장 (POST /backend/notifications)
    @PostMapping("/backend/notifications")
    public ResponseEntity<ApiResponseDto<Notification>> create(@RequestBody Notification notification) {
        Notification saved = notificationService.create(notification);
        return ResponseEntity.ok(ApiResponseDto.success(saved));
    }

     //[외부] 사용자 알림 전체 조회 (GET /api/notifications?userId=123)
    @GetMapping("/api/notifications")
    public ResponseEntity<ApiResponseDto<List<Notification>>> findByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponseDto.success(notificationService.findByUserId(userId)));
    }

     //[외부] 알림 단건 조회 (GET /api/notifications/{id})
    @GetMapping("/api/notifications/{id}")
    public ResponseEntity<ApiResponseDto<Notification>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDto.success(notificationService.findById(id)));
    }

     //[내부] 알림 단건 읽음 처리 (PUT /backend/notifications/{id}/read)
    @PutMapping("/backend/notifications/{id}/read")
    public ResponseEntity<ApiResponseDto<Void>> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

     //[내부] 알림 전체 읽음 처리 (PUT /backend/notifications/read-all)
     //선택사항: 아직 서비스에 메서드 없다면 나중에 구현
    @PutMapping("/backend/notifications/read-all")
    public ResponseEntity<ApiResponseDto<Void>> markAllAsRead(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            notificationService.markAsRead(id);
        }
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

     //[외부] 알림 단건 삭제 (DELETE /api/notifications/{id})
    @DeleteMapping("/api/notifications/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

     //[외부] 알림 다중 삭제 (DELETE /api/notifications)
    @DeleteMapping("/api/notifications")
    public ResponseEntity<ApiResponseDto<Void>> deleteAll(@RequestBody List<Long> ids) {
        notificationService.deleteAll(ids);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }
}

