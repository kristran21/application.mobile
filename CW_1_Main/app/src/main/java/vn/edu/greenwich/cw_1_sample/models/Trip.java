package vn.edu.greenwich.cw_1_sample.models;

import java.io.Serializable;

public class Trip implements Serializable {
    public Long tripId;
    public String tripName;
    public String destination;
    public String tripDate;
    public int requiresRiskAssessment;
    public String description;
    public String importanceNote;
    public String budgetRange;
    public Trip() {
    }

    public Trip(Long tripId, String tripName, String destination, String tripDate, int requiresRiskAssessment, String description, String importanceNote, String budgetRange) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.destination = destination;
        this.tripDate = tripDate;
        this.requiresRiskAssessment = requiresRiskAssessment;
        this.description = description;
        this.importanceNote = importanceNote;
        this.budgetRange = budgetRange;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public int getRequiresRiskAssessment() {
        return requiresRiskAssessment;
    }

    public void setRequiresRiskAssessment(int requiresRiskAssessment) {
        this.requiresRiskAssessment = requiresRiskAssessment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportanceNote() {
        return importanceNote;
    }

    public void setImportanceNote(String importanceNote) {
        this.importanceNote = importanceNote;
    }

    public String getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(String budgetRange) {
        this.budgetRange = budgetRange;
    }
}