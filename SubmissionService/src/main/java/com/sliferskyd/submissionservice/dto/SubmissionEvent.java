package com.sliferskyd.submissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionEvent {
    private String id;
    private String problemId;
    private String userId;
    private String language;
    private String code;
}
