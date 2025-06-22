package com.studyhub.notification.domain.entity;

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
    private String type;  // ex: MATCHING, CHAT 등

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private String link;

    @Column(nullable = false)
    private boolean isRead;

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
}
