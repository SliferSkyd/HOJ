package com.sliferskyd.judgeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionEvent {
    private String submissionId;
    private String code;
    private String language;
    private String problemId;
}
