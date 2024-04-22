package com.sliferskyd.submissionservice.controller;

import com.sliferskyd.submissionservice.dto.SubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubmission(@RequestBody SubmissionRequest submissionRequest) {

    }

}
