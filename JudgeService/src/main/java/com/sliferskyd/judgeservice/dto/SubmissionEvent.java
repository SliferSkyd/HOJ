package com.sliferskyd.judgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionEvent {
    private String submissionId;
    private String code;
    private String language;
    private String problemId;
}
