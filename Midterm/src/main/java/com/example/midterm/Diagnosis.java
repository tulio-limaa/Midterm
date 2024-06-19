package com.example.midterm;

public class Diagnosis {

    private String patientID;
    private String symptoms;
    private String diagnosis;
    private String medicines;
    private Boolean wardRequired;

    public Diagnosis(String patientID, String symptoms, String diagnosis, String medicines, Boolean wardRequired) {
        this.patientID = patientID;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.medicines = medicines;
        this.wardRequired = wardRequired;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public Boolean getWardRequired() {
        return wardRequired;
    }
}
