package proyek_pbo;

import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Proyek_pbo extends Application {

    private Schedule schedule = new Schedule();
    private Stage mainStage; // Untuk mengganti scene

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        showLoginPage(); // Tampilkan halaman login saat aplikasi dimulai
    }

    private void showLoginPage() {
    VBox loginLayout = new VBox(10);
    loginLayout.setPadding(new Insets(15));
    loginLayout.setStyle("-fx-font-family: Arial; -fx-alignment: center;");
    loginLayout.getStyleClass().add("login-layout"); // Tambahkan kelas CSS

    Label lblTitle = new Label("Login");
    lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    TextField tfUsername = new TextField();
    tfUsername.setPromptText("Username");
    PasswordField pfPassword = new PasswordField();
    pfPassword.setPromptText("Password");
    Button btnLogin = new Button("Login");
    Button btnRegister = new Button("Register");
    Label lblMessage = new Label();

    // Login Button Action
    btnLogin.setOnAction(e -> {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Semua field harus diisi!");
            return;
        }

        if (authenticateUser(username, password)) {
            showMainPage();
        } else {
            lblMessage.setText("Username atau password salah!");
        }
    });

    // Pindah ke halaman register
    btnRegister.setOnAction(e -> showRegisterPage());

    loginLayout.getChildren().addAll(lblTitle, tfUsername, pfPassword, btnLogin, btnRegister, lblMessage);

    Scene loginScene = new Scene(loginLayout, 300, 200);
    loginScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    mainStage.setTitle("Login");
    mainStage.setScene(loginScene);
    mainStage.show();
}

    private void showRegisterPage() {
        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new Insets(15));
        registerLayout.setStyle("-fx-font-family: Arial; -fx-alignment: center;");

        Label lblTitle = new Label("Register");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");
        Button btnRegister = new Button("Register");
        Button btnBackToLogin = new Button("Kembali ke Login");
        Label lblMessage = new Label();

        // Register Button Action
        btnRegister.setOnAction(e -> {
            String username = tfUsername.getText();
            String password = pfPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Semua field harus diisi!");
                return;
            }

            // Simpan pengguna baru ke database
            if (registerUser(username, password)) {
                lblMessage.setText("Registrasi berhasil! Silakan login.");
            } else {
                lblMessage.setText("Username sudah terdaftar!");
            }
        });

        // Pindah kembali ke halaman login
        btnBackToLogin.setOnAction(e -> showLoginPage());

        registerLayout.getChildren().addAll(lblTitle, tfUsername, pfPassword, btnRegister, btnBackToLogin, lblMessage);

        Scene registerScene = new Scene(registerLayout, 300, 200);
        mainStage.setTitle("Register");
        mainStage.setScene(registerScene);
        mainStage.show();
    }

    private void showMainPage() {
        Label labelTitle = new Label("Manajemen Jadwal Mata Kuliah");
        labelTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField tfCourseCode = new TextField();
        tfCourseCode.setPromptText("Kode Mata Kuliah");
        TextField tfCourseName = new TextField();
        tfCourseName.setPromptText("Nama Mata Kuliah");
        TextField tfLecturerName = new TextField();
        tfLecturerName.setPromptText("Nama Dosen");
        TextField tfLecturerId = new TextField();
        tfLecturerId.setPromptText("ID Dosen");
        TextField tfScheduleTime = new TextField();
        tfScheduleTime.setPromptText("Waktu (contoh: Monday 9 AM)");

        Button btnAddCourse = new Button("Tambah Mata Kuliah");
        Button btnUpdateCourse = new Button("Ubah Mata Kuliah");
        Button btnRemoveCourse = new Button("Hapus Mata Kuliah");
        Button btnViewSchedule = new Button("Lihat Jadwal");

        TextArea taScheduleDisplay = new TextArea();
        taScheduleDisplay.setEditable(false);
        taScheduleDisplay.setPrefHeight(200);

        btnAddCourse.setOnAction(e -> addCourse(tfCourseCode, tfCourseName, tfLecturerName, tfLecturerId, tfScheduleTime));
        btnUpdateCourse.setOnAction(e -> updateCourse(tfCourseCode, tfCourseName, tfLecturerName, tfLecturerId, tfScheduleTime));
        btnRemoveCourse.setOnAction(e -> removeCourse(tfCourseCode));
        btnViewSchedule.setOnAction(e -> viewSchedule(taScheduleDisplay));

        VBox formBox = new VBox(10, tfCourseCode, tfCourseName, tfLecturerName, tfLecturerId, tfScheduleTime);
        HBox buttonBox = new HBox(10, btnAddCourse, btnUpdateCourse, btnRemoveCourse, btnViewSchedule);
        VBox root = new VBox(15, labelTitle, formBox, buttonBox, taScheduleDisplay);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-font-family: Arial;");

        Scene mainScene = new Scene(root, 500, 400);
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        mainStage.setTitle("Manajemen Jadwal Mata Kuliah");
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    private void addCourse(TextField tfCourseCode, TextField tfCourseName, TextField tfLecturerName, TextField tfLecturerId, TextField tfScheduleTime) {
        String courseCode = tfCourseCode.getText();
        String courseName = tfCourseName.getText();
        String lecturerName = tfLecturerName.getText();
        String lecturerId = tfLecturerId.getText();
        String scheduleTime = tfScheduleTime.getText();

        if (courseCode.isEmpty() || courseName.isEmpty() || lecturerName.isEmpty() || lecturerId.isEmpty() || scheduleTime.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Semua field harus diisi!");
            return;
        }

        Lecturer lecturer = new Lecturer(lecturerName, lecturerId);
        Course course = new Course(courseCode, courseName, lecturer, scheduleTime);

        schedule.addCourse(course);
        showAlert(Alert.AlertType.INFORMATION, "Informasi", "Mata kuliah berhasil ditambahkan.");
        clearFields(tfCourseCode, tfCourseName, tfLecturerName, tfLecturerId, tfScheduleTime);
    }

    private void updateCourse(TextField tfCourseCode, TextField tfCourseName, TextField tfLecturerName, TextField tfLecturerId, TextField tfScheduleTime) {
        String courseCode = tfCourseCode.getText();
        String courseName = tfCourseName.getText();
        String lecturerName = tfLecturerName.getText();
        String lecturerId = tfLecturerId.getText();
        String scheduleTime = tfScheduleTime.getText();

        if (courseCode.isEmpty() || courseName.isEmpty() || lecturerName.isEmpty() || lecturerId.isEmpty() || scheduleTime.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Semua field harus diisi!");
            return;
        }

        Course existingCourse = schedule.findCourse(courseCode);
        if (existingCourse == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Mata kuliah dengan kode " + courseCode + " tidak ditemukan.");
            return;
        }

        Lecturer lecturer = new Lecturer(lecturerName, lecturerId);
        Course updatedCourse = new Course(courseCode, courseName, lecturer, scheduleTime);
        schedule.updateCourse(updatedCourse);
        showAlert(Alert.AlertType.INFORMATION, "Informasi", "Mata kuliah berhasil diperbarui.");
        clearFields(tfCourseCode, tfCourseName, tfLecturerName, tfLecturerId, tfScheduleTime);
    }

    private void removeCourse(TextField tfCourseCode) {
        String courseCode = tfCourseCode.getText();
        if (courseCode.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Masukkan kode mata kuliah yang akan dihapus!");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Hapus");
        confirmAlert.setContentText("Apakah Anda yakin ingin menghapus mata kuliah dengan kode " + courseCode + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (schedule.removeCourse(courseCode)) {
                showAlert(Alert.AlertType.INFORMATION, "Informasi", "Mata kuliah berhasil dihapus.");
                clearFields(tfCourseCode);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Mata kuliah dengan kode " + courseCode + " tidak ditemukan.");
            }
        }
    }

    private void viewSchedule(TextArea taScheduleDisplay) {
        List<Course> courses = schedule.getCourses();
        StringBuilder scheduleText = new StringBuilder();

        if (courses.isEmpty()) {
            scheduleText.append("Belum ada mata kuliah yang terdaftar.");
        } else {
            scheduleText.append("Daftar Mata Kuliah:\n\n");
            for (Course course : courses) {
                scheduleText.append("Kode: ").append(course.getCourseCode()).append("\n")
                            .append("Nama: ").append(course.getCourseName()).append("\n")
                            .append("Dosen: ").append(course.getLecturer().getName())
                            .append(" (").append(course.getLecturer().getId()).append(")\n")
                            .append("Waktu: ").append(course.getScheduleTime()).append("\n")
                            .append("------------------------\n");
            }
        }

        taScheduleDisplay.setText(scheduleText.toString());
    }

    private boolean authenticateUser(String username, String password) {
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}