package raisetech.SutudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.SutudentManagement.controller.converter.StudentConverter;
import raisetech.SutudentManagement.data.CourseApplication;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
import raisetech.SutudentManagement.domain.StudentCourseStatus;
import raisetech.SutudentManagement.domain.StudentDetail;
import raisetech.SutudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).searchStudentList();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void idに紐づく受講生詳細情報の検索() {
    String id = "test-id";
    Student student = new Student();
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 申し込み状況の全件検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<CourseApplication> courseApplicationList = new ArrayList<>();
    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchCourseApplicationList()).thenReturn(courseApplicationList);

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatusList();

    verify(repository, times(1)).searchCourseApplicationList();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentCourseStatusList(studentList, studentCourseList, courseApplicationList);
  }

  @Test
  void idに紐づく申し込み状況検索() {
    String id = "test-id";
    Student student = new Student();
    student.setId(id);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<CourseApplication> courseApplicationList = new ArrayList<>();

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchCourseApplicationList()).thenReturn(courseApplicationList);

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatus(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourseList();
    verify(repository, times(1)).searchCourseApplicationList();
  }

  @Test
  void 正常に受講生が登録されること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    StudentDetail actual = sut.registerStudent(studentDetail);

    assertEquals(studentDetail, actual);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
  }

  @Test
  void 正常に申し込み状況が登録されること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    CourseApplication courseApplication = new CourseApplication();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseApplication> courseApplicationList = List.of(courseApplication);
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus(student, studentCourseList, courseApplicationList);

    StudentCourseStatus actual = sut.registerStudentCourseStatus(studentCourseStatus);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
    verify(repository, times(1)).registerCourseApplication(courseApplication);
  }

  @Test
  void 正常に受講生詳細の更新処理がされること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 正常に申し込み状況の更新処理がされること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    CourseApplication courseApplication = new CourseApplication();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseApplication> courseApplicationList = List.of(courseApplication);
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus(student, studentCourseList, courseApplicationList);

    sut.updateStudentCourseStatus(studentCourseStatus);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
    verify(repository, times(1)).updateCourseApplication(courseApplication);
  }
}