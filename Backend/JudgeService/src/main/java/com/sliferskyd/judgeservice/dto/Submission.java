package com.sliferskyd.judgeservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Submission {
    private String id;
    private String problemId;
    private String userId;
    private String language;
    private String code;
    private String status;
    private List<Result> results;
}
