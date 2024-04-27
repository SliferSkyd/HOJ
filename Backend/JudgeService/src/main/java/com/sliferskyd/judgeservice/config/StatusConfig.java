package com.sliferskyd.judgeservice.config;

import org.springframework.context.annotation.Configuration;

import java.util.List;

public class StatusConfig {
    public static final List<String> exitCode = List.of(new String[]{
            "Accepted",
            "Compilation Error",
            "Wrong Answer",
            "Runtime Error",
            "Time Limit Exceeded",
            "Memory Limit Exceeded",
            "Output Limit Exceeded",
            "Segmentation Fault",
            "Floating Point Error",
            "Unknown Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error",
            "Internal Error"
    });
}
