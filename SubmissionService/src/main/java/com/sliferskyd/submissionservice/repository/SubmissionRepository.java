package com.sliferskyd.submissionservice.repository;

import com.sliferskyd.submissionservice.model.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
    List<Submission> findByUserId(String userId);
    List<Submission> findByProblemId(String problemId);
    Optional<Submission> findById(String id);
}
