package com.studyhub.notification.domain.entity;

import com.studyhub.notification.event.message.chat.ChatEvent;
import com.studyhub.notification.event.message.study.StudyEvent;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    private Long userId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isRead;

    @Column(unique = true)
    private String externalId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void markAsRead() {
        this.isRead = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamps() {
        this.updatedAt = LocalDateTime.now();
    }

    public void initializeTimestamps() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Notification of(StudyEvent event) {
        Notification notification = Notification.builder()
                .userId(event.getData().getUserId())
                .type(event.getEventType())
                .title("스터디 알림")
                .content(event.getEventType().equals("STUDY_CREATED") ? "새로운 스터디가 생성되었습니다." : "스터디가 삭제되었습니다.")
                .externalId(event.getEventType() + ":" + event.getData().getStudyId())
                .isRead(false)
                .build();
        notification.initializeTimestamps();
        return notification;
    }

    public static Notification of(ChatEvent event) {
        Notification notification = Notification.builder()
                .userId(event.getData().getSpeakerId())
                .type(event.getEventType())
                .title("새로운 메시지 도착")
                .content(event.getData().getContent())
                .externalId("USER_MESSAGE:" + event.getData().getStudyChatMessageId())
                .isRead(false)
                .build();
        notification.initializeTimestamps();
        return notification;
    }
}
