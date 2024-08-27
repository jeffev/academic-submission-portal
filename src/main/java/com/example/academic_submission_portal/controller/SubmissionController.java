package com.example.academic_submission_portal.controller;

import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.repository.SubmissionRepository;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class SubmissionController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SubmissionRepository submissionRepository;

    private Submission submission = new Submission();
    private List<Submission> submissions;
    private UploadedFile file;

    public SubmissionController() {
        //submissions = submissionRepository.findAll();
    }

    public void upload() {
        if (file != null) {
            submission.setFileData(file.getContent());
            submission.setFileName(file.getFileName());
            submission.setSubmissionDate(new Date());
            submissionRepository.save(submission);
            submissions = submissionRepository.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Trabalho enviado com sucesso"));
            submission = new Submission();
            file = null;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, selecione um arquivo", null));
        }
    }

    // Getters e Setters

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
