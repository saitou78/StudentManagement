package raisetech.SutudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全権検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生検索が行えること() {
    Student actual = sut.searchStudent("1");
    assertThat(actual.getId()).isEqualTo("1");
  }

  @Test
  void 存在しないIDで検索したときに空で返すこと() {
    Student actual = sut.searchStudent("9999");
    assertThat(actual).isNull();
  }

  @Test
  void 受講生コース情報の全権検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生詳細の検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourse("1");
    assertThat(actual.size()).isEqualTo(1);
  }

  @Test
  void  受講生コースを2件持つ受講生IDを検索したとき検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourse("5");
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(34);
    student.setGender("男");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentInformationId("1");
    studentCourse.setCourseName("javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setFinalDate(LocalDateTime.now().plusYears(1));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setId("5");
    student.setName("田中浩二");
    student.setFrigana("タナカコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(34);
    student.setGender("男");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudent("5");
    assertThat(actual.getName()).isEqualTo("田中浩二");
  }

  @Test
  void 受講生コース情報の更新が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentInformationId("1");
    studentCourse.setCourseName("javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setFinalDate(LocalDateTime.now().plusYears(1));

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourseTest("1");
    assertThat(actual.getCourseName()).isEqualTo("javaコース");
  }
}

