package raisetech.SutudentManagement.controller;

import static java.lang.reflect.Array.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
import raisetech.SutudentManagement.domain.StudentDetail;
import raisetech.SutudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws Exception {
    when(service.searchStudentList()).thenReturn(List.of((new StudentDetail())));

    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"student\":null,\"studentCourseList\":null}]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "999";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
            {
                     "student": {\s
                         "name" : "榎並浩二",\s
                         "frigana" : "エナミコウジ",\s
                         "nickname" : "こうじ",\s
                         "email" : "kouji@mail",\s
                         "address" : "千葉",\s
                         "age" : 20,\s
                         "gender" : "男",\s
                         "remark" : ""
                     },
                     "studentCourseList" : [
                         {
                             "courseName" : "javaコース"
                         }
                     ]
                 }
            """
    ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void  受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
            {
                               "student": {
                                   "id": "13",
                                   "name": "榎並浩二",
                                   "frigana": "エナミコウジ",
                                   "nickname": "こうじ",
                                   "email": "kouji@mail",
                                   "address": "千葉",
                                   "age": 20,
                                   "gender": "男",
                                   "remark": ""
                               },
                               "studentCourseList": [
                                   {
                                       "id": "15",
                                       "studentInformationId": "13",
                                       "courseName": "javaコース",
                                       "startDate": "2024-04-27T17:50:43.322143",
                                       "finalDate": "2025-04-27T17:50:43.322143"
                                   }
                               ]
                           }
            """
    ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/studentException"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("エラーが発生しました"));
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字のみ入力してください");
  }

  @Test
  void 受講生詳細の受講生でnameが空の時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("999");
    student.setName("");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("名前を入力してください");
  }

  @Test
  void 受講生詳細の受講生でfriganaが空の時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("999");
    student.setName("榎並浩二");
    student.setFrigana("");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("フリガナを入力してください");
  }

  @Test
  void 受講生詳細の受講生でemailが空の時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("999");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("");
    student.setAddress("奈良県");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("メールアドレスを入力してください");
  }

  @Test
  void 受講生詳細の受講生でaddressが空の時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("地域を入力してください");
  }

  @Test
  void 受講生詳細の受講生でgenderが空の時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("1");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setGender("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("性別を入力してください");
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("榎並浩二");
    student.setFrigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コースでIDに数字以外を用いたときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("テストID");
    studentCourse.setCourseName("javaコース");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数字を入力してください");
  }

  @Test
  void 受講生詳細の受講生コースでcourseNameが空の時入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("999");
    studentCourse.setCourseName("");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("コース名を入力してください");
  }

  @Test
  void 受講生詳細の受講生コースで適切な値を入力したときに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("999");
    studentCourse.setCourseName("javaコース");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(0);
  }
}