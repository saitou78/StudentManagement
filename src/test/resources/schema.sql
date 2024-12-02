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
    id INT AUTO_INCREMENT PRIMARY KEY ,
    student_information_id INT NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    start_date DATETIME NOT NULL,
    final_date DATETIME NOT NULL,
    FOREIGN KEY (student_information_id) REFERENCES students(id)
);