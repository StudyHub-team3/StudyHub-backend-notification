package com.studyhub.notification.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.notification.domain.dto.NotificationRequestDto;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationMessageHandler {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "matching-complete", groupId = "notification-group")
    public void handleMatchingComplete(String message) {
        try {
            log.info("[Kafka] 매칭 완료 이벤트 수신: {}", message);
            // DTO로 파싱
            NotificationRequestDto dto = objectMapper.readValue(message, NotificationRequestDto.class);
            notificationService.create(dto);
        } catch (Exception e) {
            log.error("[Kafka] 매칭 완료 처리 중 예외 발생: " + e.getMessage(), e);
        }
    }
}
