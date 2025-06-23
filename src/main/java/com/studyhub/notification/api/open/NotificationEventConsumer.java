package com.studyhub.notification.api.open;

import com.studyhub.notification.domain.dto.NotificationEventDto;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(NotificationEventDto dto) {
        notificationService.save(dto);
    }
}
