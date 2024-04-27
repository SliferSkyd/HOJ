package com.sliferskyd.judgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private String input;
    private String output;
    private boolean isSample;
    private Long id;
}
