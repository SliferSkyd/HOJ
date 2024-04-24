package com.sliferskyd.submissionservice.service;

import com.sliferskyd.submissionservice.dto.SubmissionRequest;
import com.sliferskyd.submissionservice.dto.SubmissionResponse;
import com.sliferskyd.submissionservice.model.Result;
import com.sliferskyd.submissionservice.model.Submission;
import com.sliferskyd.submissionservice.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SubmissionResponse createSubmission(SubmissionRequest submissionRequest) {
        Submission submission = Submission.builder()
                .problemId(submissionRequest.getProblemId())
                .userId(submissionRequest.getUserId())
                .language(submissionRequest.getLanguage())
                .code(submissionRequest.getCode())
                .status("Running")
                .build();
        submission = submissionRepository.save(submission);
        kafkaTemplate.send("submission", submission.getId());
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

    public void updateSubmission(SubmissionRequest submissionRequest) {
        Submission submission = submissionRepository.findById(submissionRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Submission with id: " + submissionRequest.getId() + " not found"));
        submission.setCode(submissionRequest.getCode());
        submissionRepository.save(submission);
        log.info("Submission updated: {}", submission);
    }

    public List<SubmissionResponse> getSubmissionsByProblemId(String problemId) {
        List<Submission> submissions = submissionRepository.findByProblemId(problemId);
        log.info("Submissions retrieved by problemId: {}", submissions);
        return submissions.stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .problemId(submission.getProblemId())
                        .userId(submission.getUserId())
                        .language(submission.getLanguage())
                        .code(submission.getCode())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SubmissionResponse> getSubmissionsByUserId(String userId) {
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        log.info("Submissions retrieved by userId: {}", submissions);
        return submissions.stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .problemId(submission.getProblemId())
                        .userId(submission.getUserId())
                        .language(submission.getLanguage())
                        .code(submission.getCode())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SubmissionResponse> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        log.info("All submissions retrieved: {}", submissions);
        return submissions.stream()
                .map(submission -> SubmissionResponse.builder()
                        .id(submission.getId())
                        .problemId(submission.getProblemId())
                        .userId(submission.getUserId())
                        .language(submission.getLanguage())
                        .code(submission.getCode())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateSubmissionStatus(String id, List<Result> results) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission with id: " + id + " not found"));
        submission.setStatus("Completed");
        submission.setResults(results);
        submissionRepository.save(submission);
        log.info("Submission status updated: {}", submission);
    }
}
