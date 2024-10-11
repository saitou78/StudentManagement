package raisetech.SutudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.SutudentManagement.controller.converter.StudentConverter;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
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
   * 受講生一覧検索
   * 全権検索、条件指定なし
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  /**
   * 受講生検索
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定
   *
   * @param id　受講生ID
   * @return　受講生
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentsCourses = repository.searchStudentsCourses(student.getId());
    return new StudentDetail(student, studentsCourses);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    for (StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      studentCourse.setStudentInformationId(studentDetail.getStudent().getId());
      studentCourse.setStartDate(LocalDateTime.now());
      studentCourse.setFinalDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentCourse);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      repository.updateStudentsCourses(studentCourse);
    }
  }
}
