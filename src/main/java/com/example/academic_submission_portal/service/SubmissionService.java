package com.example.academic_submission_portal.service;

import com.example.academic_submission_portal.exception.SubmissionNotFoundException;
import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> findById(Long id) {
        return submissionRepository.findById(id);
    }

    public Submission findByIdOrThrow(Long id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found with id: " + id));
    }

    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Submission update(Submission submission) {
        return submissionRepository.save(submission);
    }

    public void delete(Long id) {
        submissionRepository.deleteById(id);
    }
}
