package com.sliferskyd.problemservice.controller;

import com.sliferskyd.problemservice.dto.ProblemRequest;
import com.sliferskyd.problemservice.dto.ProblemResponse;
import com.sliferskyd.problemservice.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {
    @Autowired
    private final ProblemService problemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProblem(@RequestBody ProblemRequest problemRequest) {
        problemService.createProblem(problemRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProblemResponse> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProblemResponse getProblemById(@PathVariable("id") String id) {
        return problemService.getProblemById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProblemById(@PathVariable("id") String id) {
        problemService.deleteProblemById(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProblemById(@PathVariable("id") String id, @RequestBody ProblemRequest problemRequest) {
        problemService.updateProblemById(id, problemRequest);
    }
}
