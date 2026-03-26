import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path DRUG_FILE = Path.of("drugs.txt");

    // PostgreSQL database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/qap4";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    saveDrugToFile(scanner);
                    break;
                case "2":
                    readDrugsFromFile();
                    break;
                case "3":
                    savePatientToDatabase(scanner);
                    break;
                case "4":
                    readPatientsFromDatabase();
                    break;
                case "5":
                    deletePatientFromDatabase(scanner);
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Choose 0-5.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Pharmacy Menu =====");
        System.out.println("1. Save data to a file (Drug)");
        System.out.println("2. Read data from the file (Drug)");
        System.out.println("3. Save data to the database (Patient)");
        System.out.println("4. Read data from the database (Patient)");
        System.out.println("5. Delete patient from database");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    // ==================== FILE I/O (DRUG) ====================

    private static void saveDrugToFile(Scanner scanner) {
        try {
            System.out.print("Enter Drug ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Drug Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Drug Cost: ");
            double cost = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Dosage: ");
            String dosage = scanner.nextLine().trim();

            Drug drug = new Drug(id, name, cost, dosage);
            appendDrug(drug);
            System.out.println("Drug saved to " + DRUG_FILE + ".");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number entered. Drug was not saved.");
        } catch (IOException ex) {
            System.out.println("Could not save file: " + ex.getMessage());
        }
    }

    private static void appendDrug(Drug drug) throws IOException {
        String csvLine = drug.getDrugId()
                + "," + drug.getDrugName()
                + "," + drug.getDrugCost()
                + "," + drug.getDosage();

        Files.writeString(
                DRUG_FILE,
                csvLine + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    private static void readDrugsFromFile() {
        try {
            if (!Files.exists(DRUG_FILE)) {
                System.out.println("No file found yet. Save a drug first.");
                return;
            }

            List<Drug> drugs = loadDrugs();
            if (drugs.isEmpty()) {
                System.out.println("File is empty.");
                return;
            }

            System.out.println("--- Drugs in file ---");
            for (Drug drug : drugs) {
                System.out.println(drug);
            }
        } catch (IOException ex) {
            System.out.println("Could not read file: " + ex.getMessage());
        }
    }

    private static List<Drug> loadDrugs() throws IOException {
        List<String> lines = Files.readAllLines(DRUG_FILE);
        List<Drug> drugs = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            String[] parts = trimmed.split(",", -1);
            if (parts.length != 4) {
                System.out.println("Skipping invalid row: " + line);
                continue;
            }

            try {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double cost = Double.parseDouble(parts[2].trim());
                String dosage = parts[3].trim();
                drugs.add(new Drug(id, name, cost, dosage));
            } catch (NumberFormatException ex) {
                System.out.println("Skipping invalid row: " + line);
            }
        }

        return drugs;
    }

    // ==================== DATABASE I/O (PATIENT) ====================

    private static void savePatientToDatabase(Scanner scanner) {
        try {
            System.out.print("Enter Patient ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Patient First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter Patient Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Enter Patient DOB (YYYYMMDD): ");
            int dob = Integer.parseInt(scanner.nextLine().trim());

            Patient patient = new Patient(id, firstName, lastName, dob);
            insertPatient(patient);
            System.out.println("Patient saved to database.");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number entered. Patient was not saved.");
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }

    private static void insertPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient (patient_id, patient_first_name, patient_last_name, patient_dob) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patient.getPatientId());
            stmt.setString(2, patient.getPatientFirstName());
            stmt.setString(3, patient.getPatientLastName());
            stmt.setInt(4, patient.getPatientDOB());

            stmt.executeUpdate();
        }
    }

    private static void readPatientsFromDatabase() {
        try {
            List<Patient> patients = loadPatients();
            if (patients.isEmpty()) {
                System.out.println("No patients found in database.");
                return;
            }

            System.out.println("--- Patients in database ---");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }

    private static void deletePatientFromDatabase(Scanner scanner) {
        try {
            System.out.print("Enter Patient ID to delete: ");
            int patientId = Integer.parseInt(scanner.nextLine().trim());
            int rowsDeleted = deletePatientById(patientId);

            if (rowsDeleted == 0) {
                System.out.println("No patient found with ID " + patientId + ".");
            } else {
                System.out.println("Patient deleted successfully.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number entered. Delete was cancelled.");
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }

    private static int deletePatientById(int patientId) throws SQLException {
        String sql = "DELETE FROM patient WHERE patient_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            return stmt.executeUpdate();
        }
    }

    private static List<Patient> loadPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT patient_id, patient_first_name, patient_last_name, patient_dob FROM patient";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("patient_id");
                String firstName = rs.getString("patient_first_name");
                String lastName = rs.getString("patient_last_name");
                int dob = rs.getInt("patient_dob");

                Patient patient = new Patient(id, firstName, lastName, dob);
                patients.add(patient);
            }
        }

        return patients;
    }
}
