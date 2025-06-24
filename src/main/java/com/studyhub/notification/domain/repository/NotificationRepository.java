package com.studyhub.notification.domain.repository;

import com.studyhub.notification.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 유저 ID로 전체 알림 조회
    List<Notification> findByUserId(Long userId);

    // 외부 ID 중복 방지용
    boolean existsByExternalId(String externalId);

    // 동적 필터 + 페이징 (isRead, type 모두 nullable)
    @Query("""
        SELECT n FROM Notification n
        WHERE n.userId = :userId
        AND (:isRead IS NULL OR n.isRead = :isRead)
        AND (:type IS NULL OR n.type = :type)
    """)
    Page<Notification> findByUserIdAndOptionalFilters(
            @Param("userId") Long userId,
            @Param("isRead") Boolean isRead,
            @Param("type") String type,
            Pageable pageable
    );
}
