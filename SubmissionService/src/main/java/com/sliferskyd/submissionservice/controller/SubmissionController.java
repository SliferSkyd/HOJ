package com.sliferskyd.submissionservice.controller;

import com.sliferskyd.submissionservice.dto.SubmissionRequest;
import com.sliferskyd.submissionservice.dto.SubmissionResponse;
import com.sliferskyd.submissionservice.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubmission(@RequestBody SubmissionRequest submissionRequest) {
        submissionService.createSubmission(submissionRequest);
    }

    @GetMapping
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public SubmissionResponse getSubmissionById(@PathVariable String id) {
        return submissionService.getSubmission(id);
    }

    @GetMapping("/problem/{problemId}")
    public void getSubmissionsByProblemId(@PathVariable String problemId) {
        submissionService.getSubmissionsByProblemId(problemId);
    }

    @GetMapping("/user/{userId}")
    public void getSubmissionsByUserId(@PathVariable String userId) {
        submissionService.getSubmissionsByUserId(userId);
    }

    @PutMapping
    public void updateSubmission(@RequestBody SubmissionRequest submissionRequest) {
        submissionService.updateSubmission(submissionRequest);
    }
}
