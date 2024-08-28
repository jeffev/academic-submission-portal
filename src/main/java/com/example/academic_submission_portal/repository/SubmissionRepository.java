package com.example.academic_submission_portal.repository;

import com.example.academic_submission_portal.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByTitleAndStudentName(String title, String studentName);
}
