package com.sliferskyd.problemservice.service;

import com.sliferskyd.problemservice.dto.ProblemRequest;
import com.sliferskyd.problemservice.dto.ProblemResponse;
import com.sliferskyd.problemservice.model.Problem;
import com.sliferskyd.problemservice.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService {
    private final ProblemRepository problemRepository;

    public void createProblem(ProblemRequest problemRequest) {
        // Create a new problem
        Problem problem = Problem.builder()
                .title(problemRequest.getTitle())
                .description(problemRequest.getDescription())
                .source(problemRequest.getSource())
                .testCases(problemRequest.getTestCases())
                .build();
        problemRepository.save(problem);
        log.info("Creating a new problem with title: {}", problemRequest.getTitle());
    }

    public List<ProblemResponse> getAllProblems() {
        // Get all problems
        List<Problem> problems = problemRepository.findAll();
        log.info("Getting all problems");
        return problems.stream()
                .map(problem -> ProblemResponse.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .description(problem.getDescription())
                        .source(problem.getSource())
                        .testCases(problem.getTestCases())
                        .build())
                .collect(Collectors.toList());
    }

    public ProblemResponse getProblemById(String id) {
        // Get a problem by id
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem with id: " + id + " not found"));
        log.info("Getting a problem with id: {}", id);
        return ProblemResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .source(problem.getSource())
                .testCases(problem.getTestCases())
                .build();
    }

    public void deleteProblemById(String id) {
        // Delete a problem by id
        problemRepository.deleteById(id);
        log.info("Deleting a problem with id: {}", id);
    }

    public void updateProblemById(String id, ProblemRequest problemRequest) {
        // Update a problem by id
        Problem problem = Problem.builder()
                .id(id)
                .title(problemRequest.getTitle())
                .description(problemRequest.getDescription())
                .source(problemRequest.getSource())
                .testCases(problemRequest.getTestCases())
                .build();
        problemRepository.save(problem);
        log.info("Updating a problem with id: {}", id);
    }
}
