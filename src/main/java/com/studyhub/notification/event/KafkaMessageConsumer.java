package com.studyhub.notification.event;

import com.studyhub.notification.event.message.chat.ChatEvent;
import com.studyhub.notification.event.message.study.StudyEvent;
import com.studyhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "study",
            groupId = "${spring.application.name}",
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE
                            + ":com.studyhub.notification.event.message.study.StudyEvent",
            }
    )
    public void handleStudyEvent(StudyEvent event, Acknowledgment ack) {
        log.info("[STUDY] StudyEvent consumed: {}", event);
        notificationService.handleStudyEvent(event);

        ack.acknowledge();
    }

    @KafkaListener(
            topics = "chat",
            groupId = "${spring.application.name}",
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE
                            + ":com.studyhub.notification.event.message.chat.ChatEvent",
            }
    )
    public void handleChatEvent(ChatEvent event, Acknowledgment ack) {
        log.info("[CHAT] UserMessageEvent consumed: {}", event);
        notificationService.handleUserMessage(event);

        ack.acknowledge();
    }
}
