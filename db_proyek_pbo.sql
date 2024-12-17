CREATE DATABASE course_scheduler;
USE course_scheduler;

CREATE TABLE lecturers (
    lecturer_id INT AUTO_INCREMENT PRIMARY KEY,
    lecturer_name VARCHAR(100) NOT NULL
);
ALTER TABLE courses ADD COLUMN lecturer_name VARCHAR(100);
drop table lecturers;

INSERT INTO lecturers (lecturer_name)
VALUES ('Dr. John Doe'), ('Prof. Jane Smith'), ('Dr. Alan Turing');


CREATE TABLE courses (
	lecturer_id INT PRIMARY KEY,
    lecturer_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(10) UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    schedule_time VARCHAR(50)
);
drop table courses;
INSERT INTO courses (course_code, course_name, lecturer_id, schedule_time)
VALUES ('CS101', 'Computer Science Basics', 1, 'Monday 9 AM'),
       ('CS102', 'Advanced Algorithms', 2, 'Tuesday 10 AM'),
       ('CS103', 'Pemograman Java' , 3, 'Sunday 11 AM'),
       ('CS104', 'SBD' , 4, 'Saturday 8 AM');


CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

DESCRIBE courses;

select * from courses;