package com.example.academic_submission_portal.controller;

import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.repository.SubmissionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class SubmissionController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SubmissionRepository submissionRepository;

    private Submission submission;
    private Submission selectedSubmission;
    private List<Submission> submissions;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        submissions = submissionRepository.findAll();
        submission = new Submission();
    }

    public void saveEvaluation() {
        if (selectedSubmission != null) {
            submissionRepository.save(selectedSubmission);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Avaliação salva com sucesso!"));
            submissions = submissionRepository.findAll();
        }
    }

    public void upload() {
        if (file != null && file.getContent() != null && file.getContent().length > 0) {
            try {
                submission.setFileData(file.getContent());
                submission.setFileName(file.getFileName());
                submission.setSubmissionDate(new Date());
                submissionRepository.save(submission);

                submissions = submissionRepository.findAll();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Trabalho enviado com sucesso"));

                submission = new Submission();
                file = null;
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar o trabalho: " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, selecione um arquivo válido", null));
        }
    }

    public void download(Long submissionId) {
        Submission submissionToDownload = submissionRepository.findById(submissionId).orElse(null);
        if (submissionToDownload != null && submissionToDownload.getFileData() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().setResponseContentType("application/octet-stream");
            facesContext.getExternalContext().setResponseHeader("Content-Disposition", "attachment;filename=\"" + submissionToDownload.getFileName() + "\"");
            try {
                facesContext.getExternalContext().getResponseOutputStream().write(submissionToDownload.getFileData());
                facesContext.responseComplete();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fazer download do arquivo: " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Arquivo não encontrado para download", null));
        }
    }

    // Getters e Setters

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Submission getSelectedSubmission() {
        return selectedSubmission;
    }

    public void setSelectedSubmission(Submission selectedSubmission) {
        this.selectedSubmission = selectedSubmission;
    }

    public List<Submission> getSubmissions() {
        if (submissions == null) {
            submissions = submissionRepository.findAll();
        }
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
