package raisetech.SutudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.SutudentManagement.controller.converter.StudentConverter;
import raisetech.SutudentManagement.data.CourseApplication;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
import raisetech.SutudentManagement.domain.StudentCourseStatus;
import raisetech.SutudentManagement.domain.StudentDetail;
import raisetech.SutudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス
 * 受講生の登録や検索、更新処理を行う
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索
   * 全権検索、条件指定なし
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudentList();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定
   *
   * @param id　受講生ID
   * @return　受講生詳細
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 申し込み状況詳細の全権検索
   * 条件指定なし
   *
   * @return 申し込み状況一覧
   */
  public List<StudentCourseStatus> searchStudentCourseStatusList() {
    List<Student> studentList = repository.searchStudentList();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<CourseApplication> courseApplicationList = repository.searchCourseApplicationList();
    return converter.convertStudentCourseStatusList(studentList, studentCourseList, courseApplicationList);
  }

  /**
   * 申し込み状況詳細の検索
   *
   * @param id　受講生ID
   * @return 申し込み状況
   */
  public List<StudentCourseStatus> searchStudentCourseStatus(String id) {
    Student student = repository.searchStudent(id);
    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<CourseApplication> courseApplicationList = repository.searchCourseApplicationList();
    return converter.convertStudentCourseStatusList(studentList, studentCourseList, courseApplicationList);
  }

  /**
   * 受講生詳細の登録
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定
   *
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
      initStudentsCourses(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    }
    return studentDetail;
  }

  /**
   * 申し込み状況詳細登録
   *　受講生、受講生コース情報、申し込み状況を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定
   * 申し込み状況には受講生コース情報を紐づける値を設定
   *
   * @param studentCourseStatus　申し込み状況詳細
   * @return　登録情報を付与した受講生申し込み
   */
  @Transactional
  public StudentCourseStatus registerStudentCourseStatus(StudentCourseStatus studentCourseStatus) {
    Student student = studentCourseStatus.getStudent();

    repository.registerStudent(student);
    for (StudentCourse studentCourse : studentCourseStatus.getStudentCourseList()) {
      initStudentsCourses(studentCourse, student);
      repository.registerStudentCourse(studentCourse);

      for (CourseApplication courseApplication : studentCourseStatus.getCourseApplicationList()) {
        initCourseApplication(courseApplication, studentCourse);
        repository.registerCourseApplication(courseApplication);
      }
    }
    return studentCourseStatus;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定
   *
   * @param studentCourse　受講生コース情報
   * @param student　受講生
   */
  private static void initStudentsCourses(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setStartDate(now);
    studentCourse.setFinalDate(now.plusYears(1));
  }

  /**
   * 申し込み状況を登録する際の初期情報を設定
   *
   * @param courseApplication　申し込み状況
   * @param studentCourse　受講生コース情報
   */
  private static void initCourseApplication(CourseApplication courseApplication, StudentCourse studentCourse) {
    courseApplication.setCourseId(studentCourse.getId());
    courseApplication.setStudentId(studentCourse.getStudentId());
  }

  /**
   * 受講生詳細の更新
   * 受講生と受講生コース情報をそれぞれ更新
   *
   * @param studentDetail　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
      repository.updateStudentCourse(studentCourse);
    }
  }

  /**
   * 申し込み状況詳細の更新
   * 受講生、受講生コース情報、申し込み状況をそれぞれ更新
   *
   * @param studentCourseStatus　申し込み状況詳細
   */
  @Transactional
  public void updateStudentCourseStatus(StudentCourseStatus studentCourseStatus) {
    repository.updateStudent(studentCourseStatus.getStudent());
    for (StudentCourse studentCourse : studentCourseStatus.getStudentCourseList()) {
      repository.updateStudentCourse(studentCourse);
      for (CourseApplication courseApplication : studentCourseStatus.getCourseApplicationList()) {
        repository.updateCourseApplication(courseApplication);
      }
    }
  }
}
