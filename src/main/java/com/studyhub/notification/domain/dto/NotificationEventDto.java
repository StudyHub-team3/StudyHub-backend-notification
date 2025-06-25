package com.studyhub.notification.domain.dto;

import lombok.Getter;

@Getter
public class NotificationEventDto {
    private Long userId;
    private String type;
    private String title;
    private String content;
    private String externalId;
}
