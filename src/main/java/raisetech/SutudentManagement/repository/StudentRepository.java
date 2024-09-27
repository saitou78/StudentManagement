package raisetech.SutudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCourses();

  @Insert("INSERT INTO students (id, name, frigana, nickname, email, address, age, gender, remark) VALUES (#{id}, #{name}, #{frigana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark})")
  void registerStudent(Student student);
}