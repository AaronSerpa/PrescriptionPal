package edu.sjsu.android.prescriptionpal;

public class Prescription {
    private String rx;
    private String name;
    private String patient;
    private int refills;

    public Prescription() {}

    public Prescription(String rx, String name, String patient, int refills) {
        this.rx = rx;
        this.name = name;
        this.patient = patient;
        this.refills = refills;
    }

    public String getRx() {
        return rx;
    }

    public String getName() {
        return name;
    }

    public String getPatient() {
        return patient;
    }

    public int getRefills() {
        return refills;
    }
}