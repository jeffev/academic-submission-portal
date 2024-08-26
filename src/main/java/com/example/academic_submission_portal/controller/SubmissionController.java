package com.example.academic_submission_portal.controller;

import com.example.academic_submission_portal.model.Submission;
import com.example.academic_submission_portal.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jakarta.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Controller
@Scope("view")
public class SubmissionController implements Serializable {

    @Autowired
    private SubmissionService submissionService;

    private List<Submission> submissions;
    private Submission selectedSubmission;
    private Submission newSubmission;

    @PostConstruct
    public void init() {
        submissions = submissionService.findAll();
        newSubmission = new Submission();
    }

    public void saveSubmission() {
        try {
            submissionService.save(newSubmission);
            submissions.add(newSubmission);
            newSubmission = new Submission();
            // Inform the user about the success
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Submission saved successfully."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save submission."));
        }
    }

    public void editSubmission() {
        try {
            submissionService.update(selectedSubmission);
            submissions = submissionService.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Submission updated successfully."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update submission."));
        }
    }

    public void deleteSubmission() {
        try {
            submissionService.delete(selectedSubmission.getId());
            submissions.remove(selectedSubmission);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Submission deleted successfully."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete submission."));
        }
    }

    // Getters and Setters

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Submission getSelectedSubmission() {
        return selectedSubmission;
    }

    public void setSelectedSubmission(Submission selectedSubmission) {
        this.selectedSubmission = selectedSubmission;
    }

    public Submission getNewSubmission() {
        return newSubmission;
    }

    public void setNewSubmission(Submission newSubmission) {
        this.newSubmission = newSubmission;
    }
}
