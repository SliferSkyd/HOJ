package com.sliferskyd.submissionservice.dto;

import com.sliferskyd.submissionservice.model.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponse {
    private String id;
    private String problemId;
    private String userId;
    private String language;
    private String code;
    private String status;
    private List<Result> results;
}
