package com.sliferskyd.judgeservice.service;

import com.sliferskyd.judgeservice.dto.Problem;
import com.sliferskyd.judgeservice.dto.Result;
import com.sliferskyd.judgeservice.dto.Submission;
import com.sliferskyd.judgeservice.dto.TestCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
    private final WebClient webClient;
    private final String path = "JudgeSpace/";

    private void writeCode(String code, String language) throws IOException {
        PrintWriter writer = null;
        switch (language) {
            case "python":
                writer = new PrintWriter(path + "main.py");
                break;
            case "java":
                writer = new PrintWriter(path + "Main.java");
                break;
            case "c++":
                writer = new PrintWriter(path + "main.cpp");
                break;
        }
        writer.println(code);
        writer.close();
    }

    private boolean compileCode(String language) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = null;
        switch (language) {
            case "python":
                return true;
            case "java":
                processBuilder = new ProcessBuilder("javac", "Main.java");
                break;
            case "c++":
                processBuilder = new ProcessBuilder("g++", "main.cpp");
                break;
        }
        processBuilder.directory(new File(path));
        Process process = processBuilder.start();
        process.waitFor();
        if (process.exitValue() != 0) {
            return false;
        }
        return true;
    }

    private void writeTestCases(List<TestCase> testCases) throws IOException {
        for (int i = 0; i < testCases.size(); i++) {
            PrintWriter writer = new PrintWriter(path + "test " + i + ".inp");
            writer.println(testCases.get(i).getInput());
            writer.close();
            writer = new PrintWriter(path + "test " + i + ".ans");
            writer.println(testCases.get(i).getOutput());
            writer.close();
        }
    }

    private Result runTest(int i, Double timeLimit, Integer memoryLimit, String language) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = null;
        switch (language) {
            case "python":
                processBuilder = new ProcessBuilder("python", "main.py");
                break;
            case "java":
                processBuilder = new ProcessBuilder("java", "Main");
                break;
            case "c++":
                processBuilder = new ProcessBuilder("./main");
                break;
        }
        processBuilder.directory(new File(path));
        Double startTime = (double) System.currentTimeMillis();
        Process process = processBuilder.start();
        process.waitFor(timeLimit.longValue(), TimeUnit.SECONDS);
        if (process.isAlive()) {
            process.destroy();
            return Result.builder()
                    .status("Time limit exceeded")
                    .executionTime(timeLimit)
                    .message("Time limit exceeded")
                    .isCorrect(false)
                    .build();
        }
        Double endTime = (double) System.currentTimeMillis();
        Double executionTime = endTime - startTime;

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        if (output.toString().equals(new BufferedReader(new FileReader(path + "test " + i + ".ans")).readLine())) {
            return Result.builder()
                    .status("Accepted")
                    .executionTime(executionTime)
                    .message("Accepted")
                    .isCorrect(true)
                    .build();
        }
        return Result.builder()
                .status("Wrong answer")
                .executionTime(executionTime)
                .message("Wrong answer")
                .isCorrect(false)
                .build();
    }

    private void sendResult(String status, List<Result> results) {
        log.info("Sending result: {}", status);
        webClient.post()
                .uri("http://localhost:8080/submissions")
                .bodyValue(Submission.builder()
                        .status(status)
                        .results(results)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @KafkaListener(topics = "submission")
    public void listen(Integer submissionId) throws IOException, InterruptedException {
        log.info("Received submission: {}", submissionId);
        Submission submission = webClient.get()
                .uri("http://localhost:8080/submissions/" + submissionId)
                .retrieve()
                .bodyToMono(Submission.class)
                .block();
        String problemId = submission.getProblemId();

        Problem problem = webClient.get()
                .uri("http://localhost:8080/problems/" + problemId)
                .retrieve()
                .bodyToMono(Problem.class)
                .block();

        if (!new File(path).exists()) {
            new File(path).mkdir();
        }
        String status = "Accepted";
        List<Result> results = new ArrayList<>();

        writeCode(submission.getCode(), submission.getLanguage());
        if (!compileCode(submission.getLanguage())) {
            sendResult("Compilation error", results);
        } else {
            log.info("Compilation successful");
            Double timeLimit = problem.getTimeLimit();
            Integer memoryLimit = problem.getMemoryLimit();
            List<TestCase> testCases = problem.getTestCases();
            log.info("Problem: {}", problem);

            writeTestCases(testCases);
            for (int i = 0; i < testCases.size(); i++) {
                Result currentResult = runTest(i, timeLimit, memoryLimit, submission.getLanguage());
                if (!currentResult.isCorrect() && status.equals("Accepted")) {
                    status = currentResult.getStatus();
                }
                results.add(currentResult);
            }
            sendResult(status, results);
        }
    }
}