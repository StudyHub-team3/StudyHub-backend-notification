package com.studyhub.notification.api.open;

import com.studyhub.notification.common.dto.ApiResponseDto;
import com.studyhub.notification.domain.dto.NotificationRequestDto;
import com.studyhub.notification.domain.dto.NotificationResponseDto;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class NotificationController {

    private final NotificationService notificationService;

    // [내부] Kafka 수신 후 알림 저장
    @PostMapping("/backend/notifications")
    public ResponseEntity<ApiResponseDto<NotificationResponseDto>> create(@RequestBody NotificationRequestDto dto) {
        NotificationResponseDto saved = notificationService.create(dto);
        return ResponseEntity.ok(ApiResponseDto.success(saved));
    }

    // [외부] 사용자 알림 조회 + 필터 + 페이지네이션
    @GetMapping("/api/notifications")
    public ResponseEntity<ApiResponseDto<Page<NotificationResponseDto>>> findByUserIdWithFilter(
            @RequestParam Long userId,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NotificationResponseDto> result = notificationService.findByFilter(userId, isRead, type, pageRequest);
        return ResponseEntity.ok(ApiResponseDto.success(result));
    }

    // [외부] 알림 단건 조회
    @GetMapping("/api/notifications/{id}")
    public ResponseEntity<ApiResponseDto<NotificationResponseDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDto.success(notificationService.findById(id)));
    }

    // [외부] 단건 읽음 처리
    @PostMapping("/api/notifications/read")
    public ResponseEntity<ApiResponseDto<Void>> markAsRead(@RequestParam Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

    // [내부] 전체 읽음 처리
    @PutMapping("/backend/notifications/read-all")
    public ResponseEntity<ApiResponseDto<Void>> markAllAsRead(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            notificationService.markAsRead(id);
        }
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

    // [외부] 단건 삭제
    @DeleteMapping("/api/notifications/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

    // [외부] 일괄 삭제
    @DeleteMapping("/api/notifications")
    public ResponseEntity<ApiResponseDto<Void>> deleteAll(@RequestBody List<Long> ids) {
        notificationService.deleteAll(ids);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }
}


