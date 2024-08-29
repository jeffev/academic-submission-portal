package com.example.academic_submission_portal.service;

import com.example.academic_submission_portal.exception.SubmissionNotFoundException;
import com.example.academic_submission_portal.exception.ValidationException;
import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionService submissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        submissionService.findAll();
        verify(submissionRepository).findAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Submission submission = new Submission();
        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));

        Optional<Submission> result = submissionService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(submission, result.get());
    }

    @Test
    void testFindByIdOrThrow() {
        Long id = 1L;
        Submission submission = new Submission();
        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));

        Submission result = submissionService.findByIdOrThrow(id);

        assertEquals(submission, result);
    }

    @Test
    void testFindByIdOrThrow_ThrowsException() {
        Long id = 1L;
        when(submissionRepository.findById(id)).thenReturn(Optional.empty());

        SubmissionNotFoundException thrown = assertThrows(
                SubmissionNotFoundException.class,
                () -> submissionService.findByIdOrThrow(id)
        );

        assertEquals("Envio não encontrado com o id: " + id, thrown.getMessage());
    }

    @Test
    void testSave_ValidSubmission() {
        Submission submission = new Submission();
        submission.setTitle("Title");
        submission.setStudentName("Student");
        submission.setFileName("file.pdf");
        submission.setFileData(new byte[1024]);

        when(submissionRepository.save(submission)).thenReturn(submission);

        Submission result = submissionService.save(submission);

        assertEquals(submission, result);
        verify(submissionRepository).save(submission);
    }

    @Test
    void testSave_InvalidSubmission_NoTitle() {
        Submission submission = new Submission();
        submission.setTitle(null);
        submission.setStudentName("Student");

        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> submissionService.save(submission)
        );

        assertEquals("O título é obrigatório.", thrown.getMessage());
    }

    @Test
    void testUpdate_ExistingSubmission() {
        Long id = 1L;
        Submission submission = new Submission();
        submission.setId(id);
        submission.setTitle("Title");
        submission.setStudentName("Student");
        submission.setFileName("file.pdf");
        submission.setFileData(new byte[1024]);

        when(submissionRepository.existsById(id)).thenReturn(true);
        when(submissionRepository.save(submission)).thenReturn(submission);

        Submission result = submissionService.update(submission);

        assertEquals(submission, result);
        verify(submissionRepository).save(submission);
    }

    @Test
    void testUpdate_NonExistingSubmission() {
        Long id = 1L;
        Submission submission = new Submission();
        submission.setId(id);

        when(submissionRepository.existsById(id)).thenReturn(false);

        SubmissionNotFoundException thrown = assertThrows(
                SubmissionNotFoundException.class,
                () -> submissionService.update(submission)
        );

        assertEquals("Envio não encontrado com o id: " + id, thrown.getMessage());
    }

    @Test
    void testDelete_ExistingSubmission() {
        Long id = 1L;
        when(submissionRepository.existsById(id)).thenReturn(true);

        submissionService.delete(id);

        verify(submissionRepository).deleteById(id);
    }

    @Test
    void testDelete_NonExistingSubmission() {
        Long id = 1L;
        when(submissionRepository.existsById(id)).thenReturn(false);

        SubmissionNotFoundException thrown = assertThrows(
                SubmissionNotFoundException.class,
                () -> submissionService.delete(id)
        );

        assertEquals("Envio não encontrado com o id: " + id, thrown.getMessage());
    }

    @Test
    void testIsAllowedFileType() {
        assertTrue(submissionService.isAllowedFileType("file.pdf"));
        assertTrue(submissionService.isAllowedFileType("file.docx"));
        assertFalse(submissionService.isAllowedFileType("file.txt"));
    }
}
