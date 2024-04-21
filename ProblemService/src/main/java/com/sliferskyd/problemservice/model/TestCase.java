package com.sliferskyd.problemservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private String input;
    private String output;
    private boolean isSample;
    @MongoId
    private Long id;
}
