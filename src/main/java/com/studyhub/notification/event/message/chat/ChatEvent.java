package com.studyhub.notification.event.message.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatEvent {
    private String eventType; // "USER_MESSAGE"
    private ChatEventData data;
    private String timestamp;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatEventData {
        private String studyChatId;
        private String studyChatMessageId;
        private String content;
        private Long speakerId;
    }

    public String getExternalId() {
        return data.getStudyChatMessageId();
    }
}
