package raisetech.SutudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.SutudentManagement.data.CourseApplication;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;
import raisetech.SutudentManagement.domain.StudentCourseStatus;
import raisetech.SutudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーター
 * 申し込み状況詳細を受講生、受講生コース情報、申し込み状況、もしくはその逆の変換を行うコンバーター
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピング
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる
   *
   * @param studentList　受講生一覧
   * @param studentCourseList　受講生コース情報リスト
   * @return　受講生詳細情報リスト
   */
  public List<StudentDetail> convertStudentDetails(
      List<Student> studentList,
      List<StudentCourse> studentCourseList) {

    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : studentList) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = new ArrayList<>();
      for (StudentCourse studentCourse : studentCourseList) {
        if (student.getId().equals(studentCourse.getStudentId())) {
          convertStudentCourseList.add(studentCourse);
        }
      }
      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }

  /**
   * 受講生に紐づく受講生コース情報、申し込み状況をマッピング
   * 受講生コース情報、申し込み状況は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる
   * @param studentList　受講生一覧
   * @param studentCourseList　受講生コース情報一覧
   * @param courseApplicationList　申し込み状況一覧
   * @return　申し込み状況詳細一覧
   */
  public List<StudentCourseStatus> convertStudentCourseStatusList(
      List<Student> studentList,
      List<StudentCourse> studentCourseList,
      List<CourseApplication> courseApplicationList) {

    List<StudentCourseStatus> studentCourseStatusList = new ArrayList<>();

    for (Student student : studentList) {
      StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
      studentCourseStatus.setStudent(student);

      List<StudentCourse> convertStudentCourseList = new ArrayList<>();
      for (StudentCourse studentCourse : studentCourseList) {
        if (student.getId().equals(studentCourse.getStudentId())) {
          convertStudentCourseList.add(studentCourse);

          List<CourseApplication> convertCourseApplicationList = new ArrayList<>();
          for (CourseApplication courseApplication : courseApplicationList) {
            if (studentCourse.getStudentId().equals(courseApplication.getStudentId())) {
              convertCourseApplicationList.add(courseApplication);
            }
          }
          studentCourseStatus.setCourseApplicationList(convertCourseApplicationList);
        }
      }
      studentCourseStatus.setStudentCourseList(convertStudentCourseList);
      studentCourseStatusList.add(studentCourseStatus);
    }
    return studentCourseStatusList;
  }
}