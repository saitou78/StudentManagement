package raisetech.SutudentManagement.studentConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.SutudentManagement.controller.converter.StudentConverter;
import raisetech.SutudentManagement.data.CourseApplication;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
import raisetech.SutudentManagement.domain.StudentCourseStatus;
import raisetech.SutudentManagement.domain.StudentDetail;



public class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること() {
    Student student = createStudent();

    StudentCourse studentCourse = createStudentCourse("1");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {
    Student student = createStudent();

    StudentCourse studentCourse = createStudentCourse("3");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストと申し込み状況のリストを渡して申し込み状況詳細のリストが作成できること() {
    Student student = createStudent();

    StudentCourse studentCourse = createStudentCourse("1");

    CourseApplication courseApplication = new CourseApplication();
    courseApplication.setId("1");
    courseApplication.setCourseId("1");
    courseApplication.setStudentId("1");
    courseApplication.setStatus("本申し込み");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseApplication> courseApplicationList = List.of(courseApplication);

    List<StudentCourseStatus> actual = sut.convertStudentCourseStatusList(studentList, studentCourseList, courseApplicationList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
    assertThat(actual.get(0).getCourseApplicationList()).isEqualTo(courseApplicationList);
  }

  private static Student createStudent() {
    Student student = new Student();
    student.setId("1");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(34);
    student.setGender("男");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }

  private static StudentCourse createStudentCourse(String number) {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId(number);
    studentCourse.setCourseName("javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setFinalDate(LocalDateTime.now().plusYears(1));
    return studentCourse;
  }
}
