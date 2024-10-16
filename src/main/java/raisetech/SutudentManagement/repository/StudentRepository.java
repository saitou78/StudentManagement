package raisetech.SutudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全権検索
   *
   * @return　受講生一覧（全件）
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生コース情報の全権検索
   *
   * @return　受講生コース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索
   *
   * @param studentInformationId　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_information_id = #{studentInformationId}")
  List<StudentCourse> searchStudentsCourses(String studentInformationId);

  @Insert("INSERT INTO students (name, frigana, nickname, email, address, age, gender, remark, is_deleted) "
      + "VALUES (#{name}, #{frigana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_information_id, course_name, start_date, final_date)"
          + "VALUES(#{studentInformationId}, #{courseName}, #{startDate}, #{finalDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentCourse studentCourse);

  @Update("UPDATE students SET name = #{name}, frigana = #{frigana}, nickname = #{nickname}, "
      + "email = #{email}, address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentsCourses(StudentCourse studentCourse);
}