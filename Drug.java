public class Drug {
    private int drugId;
    private String drugName;
    private double drugCost;
    private String dosage;

    public Drug(int drugId, String drugName, double drugCost, String dosage) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugCost = drugCost;
        this.dosage = dosage;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugCost(double drugCost) {
        this.drugCost = drugCost;
    }

    public double getDrugCost() {
        return drugCost;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    public String toString() {
        return "Drug ID: " + drugId + ", Name: " + drugName + ", Cost: $" + drugCost + ", Dosage: " + dosage;
    }
    
}