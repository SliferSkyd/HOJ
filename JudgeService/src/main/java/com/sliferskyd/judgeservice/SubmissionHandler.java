package com.sliferskyd.judgeservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class SubmissionHandler {
    @KafkaListener(topics = "submission")
    public void listen(SubmissionEvent submissionEvent) {
        log.info("Received submission: {}", submissionEvent.getSubmissionId());
        DockerExecutor dockerExecutor = new DockerExecutor();
        try {
            dockerExecutor.executeSubmission(submissionEvent.getLanguage(), submissionEvent.getCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
