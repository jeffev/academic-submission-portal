package com.example.academic_submission_portal.service;

import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public Submission findById(Long id) {
        return submissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Submission not found"));
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
