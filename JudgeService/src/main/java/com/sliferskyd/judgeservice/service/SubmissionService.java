package com.sliferskyd.judgeservice.service;

import com.sliferskyd.judgeservice.dto.Problem;
import com.sliferskyd.judgeservice.dto.SubmissionEvent;
import com.sliferskyd.judgeservice.dto.TestCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
    private final WebClient webClient;

    @KafkaListener(topics = "submission")
    public void listen(SubmissionEvent submissionEvent) throws IOException, InterruptedException {
        log.info("Received submission: {}", submissionEvent.getSubmissionId());
        Problem problem = webClient.get()
                .uri("http://localhost:8080/problems/" + submissionEvent.getProblemId())
                .retrieve()
                .bodyToMono(Problem.class)
                .block();
        Double timeLimit = problem.getTimeLimit();
        Integer memoryLimit = problem.getMemoryLimit();
        List<TestCase> testCases = problem.getTestCases();
        log.info("Problem: {}", problem);

        for (TestCase testCase : testCases) {
            PrintWriter writer = new PrintWriter("../data/Main.java");
            writer.println(submissionEvent.getCode());
            writer.close();
            log.info("Running test case: {}", testCase);
            writer = new PrintWriter("../data/input.txt");
            writer.println(testCase.getInput());
            writer.close();
            writer = new PrintWriter("../data/answer.txt");
            writer.println(testCase.getOutput());
            writer.close();

            // Run the code
            String[] command = {"python", "./JudgeService/src/main/java/com/sliferskyd/judgeservice/tool/Judge.py", "--timeout", timeLimit.toString(), "--memory_limit", memoryLimit.toString()};
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);
        }
    }
}
