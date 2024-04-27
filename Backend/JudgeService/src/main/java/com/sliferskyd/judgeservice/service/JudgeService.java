package com.sliferskyd.judgeservice.service;

import com.sliferskyd.judgeservice.dto.Problem;
import com.sliferskyd.judgeservice.dto.Result;
import com.sliferskyd.judgeservice.dto.Submission;
import com.sliferskyd.judgeservice.dto.TestCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.sliferskyd.judgeservice.config.StatusConfig.exitCode;

@Slf4j
@RequiredArgsConstructor
@Service
public class JudgeService {
    private final WebClient webClient;
    private final String path = "data/";

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

    private void writeTestCases(TestCase testCase) throws IOException {
        PrintWriter writer = new PrintWriter(path + "test.in");
        writer.println(testCase.getInput());
        writer.close();
        writer = new PrintWriter(path + "test.ans");
        writer.println(testCase.getOutput());
        writer.close();
    }

    private Result runTest(Integer timeLimit, Integer memoryLimit, String language) throws IOException, InterruptedException {
        File dataFolder = new File(path);
        ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-v", dataFolder.getAbsolutePath() + ":/app/data", "judger", "./judger", "-c", Integer.toString(timeLimit), "-m", Integer.toString(memoryLimit), "-l", language);
        File resultFile = new File(path + "result.txt");
        processBuilder.redirectOutput(resultFile);
        processBuilder.start().waitFor();
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));
        String line;
        Result result = new Result();
        while ((line = reader.readLine()) != null) {
            List<String> cur = List.of(line.split(" "));
            if (cur.get(0).equals("timeUsed:")) {
                result.setTimeUsed(Integer.parseInt(cur.get(1)));
            } else if (cur.get(0).equals("memoryUsed:")) {
                result.setMemoryUsed(Integer.parseInt(cur.get(1)));
            } else if (cur.get(0).equals("status:")) {
                result.setStatus(exitCode.get(Integer.parseInt(cur.get(1))));
            }
        }
        return result;
    }

    private void sendResult(String id, List<Result> results) {
        webClient.post()
                .uri("http://localhost:8080/submissions/" + id)
                .bodyValue(results)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @KafkaListener(topics = "submission", groupId = "judge")
    public void listen(String submissionId) throws IOException, InterruptedException {
        log.info("Received submission: {}", submissionId);
        System.out.println("Received submission: " + submissionId);
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
        List<Result> results = new ArrayList<>();

        writeCode(submission.getCode(), submission.getLanguage());

        Integer timeLimit = problem.getTimeLimit();
        Integer memoryLimit = problem.getMemoryLimit();
        List<TestCase> testCases = problem.getTestCases();
        log.info("Problem: {}", problem);

        for (TestCase testCase : testCases) {
            writeTestCases(testCase);
            Result currentResult = runTest(timeLimit, memoryLimit, submission.getLanguage());
            results.add(currentResult);
        }
        sendResult(submissionId, results);
    }
}