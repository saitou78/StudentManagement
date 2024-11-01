package raisetech.SutudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.SutudentManagement.controller.converter.StudentConverter;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
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
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void idに紐づく受講生詳細情報の検索() {
    String studentId = "test-id";
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    StudentDetail actual = sut.searchStudent(studentId);

    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(student.getId());
  }

  @Test
  void 正常に受講生が登録されること() {
    Student student = new Student();
    StudentCourse course1 = new StudentCourse();
    StudentCourse course2 = new StudentCourse();
    List<StudentCourse> studentCourses = List.of(course1, course2);
    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    doNothing().when(repository).registerStudent(student);
    doNothing().when(repository).registerStudentCourse(course1);
    doNothing().when(repository).registerStudentCourse(course2);

    StudentDetail actual = sut.registerStudent(studentDetail);

    assertEquals(studentDetail, actual);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(course1);
    verify(repository, times(1)).registerStudentCourse(course2);
  }

  @Test
  void 正常に更新処理がされること() {
    Student student = new Student();
    StudentCourse course1 = new StudentCourse();
    StudentCourse course2 = new StudentCourse();
    List<StudentCourse> studentCourses = List.of(course1, course2);
    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    doNothing().when(repository).updateStudent(student);
    doNothing().when(repository).updateStudentCourse(course1);
    doNothing().when(repository).updateStudentCourse(course2);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(course1);
    verify(repository, times(1)).updateStudentCourse(course2);
  }
}