package com.example.midterm;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField patientIDField;
    @FXML
    private TextField symptomsField;
    @FXML
    private TextField diagnosisField;
    @FXML
    private TextField medicinesField;
    @FXML
    private CheckBox wardRequiredCheckBox;
    @FXML
    private TableView<Diagnosis> resultsTable;

    @FXML
    private TableColumn<Diagnosis, String> patientIDColumn;
    @FXML
    private TableColumn<Diagnosis, String> symptomsColumn;
    @FXML
    private TableColumn<Diagnosis, String> diagnosisColumn;
    @FXML
    private TableColumn<Diagnosis, String> medicinesColumn;
    @FXML
    private TableColumn<Diagnosis, Boolean> wardRequiredColumn;

    private Connection connect() {
        String url = "jdbc:mysql://127.0.0.1:3306/diagnosis";
        String user = "root";
        String password = "mysql123";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to MySQL has been established.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String sql = "INSERT INTO Diagnosis(PatientID, Symptoms, Diagnosis, Medicines, WardRequired) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("Connection is null.");
                return;
            }
            pstmt.setString(1, patientIDField.getText());
            pstmt.setString(2, symptomsField.getText());
            pstmt.setString(3, diagnosisField.getText());
            pstmt.setString(4, medicinesField.getText());
            pstmt.setBoolean(5, wardRequiredCheckBox.isSelected());
            pstmt.executeUpdate();
            System.out.println("Data has been saved.");
        } catch (SQLException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String sql = "SELECT * FROM Diagnosis WHERE PatientID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("Connection is null.");
                return;
            }
            pstmt.setString(1, patientIDField.getText());
            ResultSet rs = pstmt.executeQuery();

            resultsTable.getItems().clear();

            while (rs.next()) {
                Diagnosis diagnosis = new Diagnosis(
                        rs.getString("PatientID"),
                        rs.getString("Symptoms"),
                        rs.getString("Diagnosis"),
                        rs.getString("Medicines"),
                        rs.getBoolean("WardRequired")
                );
                resultsTable.getItems().add(diagnosis);
            }
            System.out.println("Search completed.");
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void initialize() {
        // Initialize table columns
        patientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        medicinesColumn.setCellValueFactory(new PropertyValueFactory<>("medicines"));
        wardRequiredColumn.setCellValueFactory(new PropertyValueFactory<>("wardRequired"));
    }
}
