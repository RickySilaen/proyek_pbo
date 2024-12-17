package proyek_pbo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    public void addCourse(Course course) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO courses (course_code, course_name, lecturer_name, lecturer_id, schedule_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getLecturer().getName());
            stmt.setString(4, course.getLecturer().getId());
            stmt.setString(5, course.getScheduleTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCourse(Course course) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "UPDATE courses SET course_name = ?, lecturer_name = ?, lecturer_id = ?, schedule_time = ? WHERE course_code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, course.getCourseName());
        stmt.setString(2, course.getLecturer().getName());
        stmt.setString(3, course.getLecturer().getId());
        stmt.setString(4, course.getScheduleTime());
        stmt.setString(5, course.getCourseCode());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM courses";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                String lecturerName = rs.getString("lecturer_name");
                String lecturerId = rs.getString("lecturer_id");
                String scheduleTime = rs.getString("schedule_time");
                
                Lecturer lecturer = new Lecturer(lecturerName, lecturerId);
                Course course = new Course(courseCode, courseName, lecturer, scheduleTime);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course findCourse(String courseCode) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM courses WHERE course_code = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String courseName = rs.getString("course_name");
                String lecturerName = rs.getString("lecturer_name");
                String lecturerId = rs.getString("lecturer_id");
                String scheduleTime = rs.getString("schedule_time");

                Lecturer lecturer = new Lecturer(lecturerName, lecturerId);
                return new Course(courseCode, courseName, lecturer, scheduleTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeCourse(String courseCode) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM courses WHERE course_code = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseCode);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
