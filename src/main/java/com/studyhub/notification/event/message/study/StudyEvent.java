package com.studyhub.notification.event.message.study;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyEvent {
    private String eventType; // "STUDY_CREATED", "STUDY_DELETED"
    private Data data;
    private String timestamp;

    @Getter
    @Setter
    public static class Data {
        private Long studyId;
        private Long userId;
    }

    public String getExternalId() {
        return eventType + ":" + data.getStudyId();
    }
}
