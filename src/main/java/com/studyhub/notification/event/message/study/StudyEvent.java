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
        private String username;
        private String creatorRole; // "MENTOR" or "MENTEE"


        @Override
        public String toString() {
            return " {\n" +
                    "\t\tstudyId=" + studyId + '\n' +
                    "\t\tuserId=" + userId + '\n' +
                    "\t\tusername=" + username + '\n' +
                    "\t\tcreatorRole=" + creatorRole + '\n' +
                    "\t}";
        }
    }

    public String getExternalId() {
        return eventType + ":" + data.getStudyId();
    }

    @Override
    public String toString() {
        return "\nStudyEvent{  \n" +
                "\teventType='" + eventType + '\n' +
                "\tdata=" + data + '\n' +
                "\ttimestamp='" + timestamp + '\n' +
                '}';
    }
}
