package com.studyhub.notification.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {
    private Long userId;
    private String type;
    private String title;
    private String content;
    private String externalId;
}

