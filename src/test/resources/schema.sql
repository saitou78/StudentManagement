CREATE TABLE IF NOT EXISTS students
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    frigana VARCHAR(255),
    nickname VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    age INT NOT NULL,
    gender VARCHAR(10),
    remark VARCHAR(255),
    is_deleted BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    start_date DATETIME NOT NULL,
    final_date DATETIME NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE IF NOT EXISTS course_applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    student_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES students_courses(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

