package com.studyhub.notification.api.open;

import com.studyhub.notification.event.message.study.StudyEvent;
import com.studyhub.notification.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/hello")
    public void hello() {
        StudyEvent studyEvent = new StudyEvent();
        studyEvent.setEventType("STUDY_CREATED");
        StudyEvent.Data data = new StudyEvent.Data();
        data.setStudyId(1L);
        data.setUserId(1L);
        data.setUsername("홍길동");
        data.setCreatorRole("MENTOR");
        studyEvent.setData(data);
        studyEvent.setTimestamp("today");

        kafkaMessageProducer.send("study", studyEvent);
    }
}
