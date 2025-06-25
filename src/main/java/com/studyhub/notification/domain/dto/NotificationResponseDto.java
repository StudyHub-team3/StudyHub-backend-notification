package com.studyhub.notification.domain.dto;

import com.studyhub.notification.domain.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {
    private Long alarmId;
    private Long userId;
    private String type;
    private String title;
    private String content;
    private boolean isRead;
    private String externalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NotificationResponseDto from(Notification notification) {
        return NotificationResponseDto.builder()
                .alarmId(notification.getAlarmId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .externalId(notification.getExternalId())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}

