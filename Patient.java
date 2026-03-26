// Drug (drugId,drugName,drugCost,dosage)

public class Patient {
    private int patientId;
    private String patientFirstName;
    private String patientLastName;
    private int patientDOB;

    public Patient(int patientId, String patientFirstName, String patientLastName, int patientDOB) {
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientDOB = patientDOB;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientDOB(int patientDOB) {
        this.patientDOB = patientDOB;
    }

    public int getPatientDOB() {
        return patientDOB;
    }

    public String toString() {
        return "Patient ID: " + patientId + ", First Name: " + patientFirstName + ", Last Name: " + patientLastName + ", DOB: " + patientDOB;
    }

}
