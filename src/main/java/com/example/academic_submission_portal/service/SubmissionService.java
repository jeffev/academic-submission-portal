package com.example.academic_submission_portal.service;

import com.example.academic_submission_portal.exception.SubmissionNotFoundException;
import com.example.academic_submission_portal.exception.ValidationException;
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

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> findById(Long id) {
        return submissionRepository.findById(id);
    }

    public Submission findByIdOrThrow(Long id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Envio não encontrado com o id: " + id));
    }

    public Submission save(Submission submission) {
        validateSubmission(submission);
        return submissionRepository.save(submission);
    }

    public Submission update(Submission submission) {
        if (!submissionRepository.existsById(submission.getId())) {
            throw new SubmissionNotFoundException("Envio não encontrado com o id: " + submission.getId());
        }
        validateSubmission(submission);
        return submissionRepository.save(submission);
    }

    public void delete(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new SubmissionNotFoundException("Envio não encontrado com o id: " + id);
        }
        submissionRepository.deleteById(id);
    }

    private void validateSubmission(Submission submission) {
        if (submission.getTitle() == null || submission.getTitle().trim().isEmpty()) {
            throw new ValidationException("O título é obrigatório.");
        }
        if (submission.getStudentName() == null || submission.getStudentName().trim().isEmpty()) {
            throw new ValidationException("O nome do estudante é obrigatório.");
        }

        if (submission.getFileData() != null && submission.getFileData().length > MAX_FILE_SIZE) {
            throw new ValidationException("O arquivo não pode exceder 10 MB.");
        }

        if (!isAllowedFileType(submission.getFileName())) {
            throw new ValidationException("Tipo de arquivo não suportado.");
        }

        if (submissionRepository.existsByTitleAndStudentName(submission.getTitle(), submission.getStudentName())) {
            throw new ValidationException("Uma submissão com este título já foi feita por este estudante.");
        }
    }

    private boolean isAllowedFileType(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return fileExtension.equals("pdf") || fileExtension.equals("docx");
    }
}
