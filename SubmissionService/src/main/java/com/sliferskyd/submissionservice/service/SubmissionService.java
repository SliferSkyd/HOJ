package com.sliferskyd.submissionservice.service;

import com.sliferskyd.submissionservice.dto.SubmissionEvent;
import com.sliferskyd.submissionservice.dto.SubmissionRequest;
import com.sliferskyd.submissionservice.dto.SubmissionResponse;
import com.sliferskyd.submissionservice.model.Submission;
import com.sliferskyd.submissionservice.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final KafkaTemplate<String, SubmissionEvent> kafkaTemplate;

    public SubmissionResponse createSubmission(SubmissionRequest submissionRequest) {
        Submission submission = Submission.builder()
                .problemId(submissionRequest.getProblemId())
                .userId(submissionRequest.getUserId())
                .language(submissionRequest.getLanguage())
                .code(submissionRequest.getCode())
                .status("PENDING")
                .build();
        submission = submissionRepository.save(submission);
        SubmissionEvent submissionEvent = SubmissionEvent.builder()
                .id(submission.getId())
                .problemId(submission.getProblemId())
                .userId(submission.getUserId())
                .language(submission.getLanguage())
                .code(submission.getCode())
                .build();
        kafkaTemplate.send("submission", submissionEvent);
        log.info("Submission created: {}", submission);
        return SubmissionResponse.builder()
                .id(submission.getId())
                .problemId(submission.getProblemId())
                .userId(submission.getUserId())
                .language(submission.getLanguage())
                .code(submission.getCode())
                .build();
    }

    public SubmissionResponse getSubmission(String id) {
        Submission submission = submissionRepository.findById(id).orElse
                (Submission.builder().build());
        log.info("Submission retrieved: {}", submission);
        return SubmissionResponse.builder()
                .id(submission.getId())
                .problemId(submission.getProblemId())
                .userId(submission.getUserId())
                .language(submission.getLanguage())
                .code(submission.getCode())
                .build();
    }

    public void updateSubmission(String id, String status, String message) {
        Submission submission = submissionRepository.findById(id).orElse
                (Submission.builder().build());
        submission.setStatus(status);
        submission.setMessage(message);
        log.info("Submission updated: {}", submission);
        submissionRepository.save(submission);
    }
}
