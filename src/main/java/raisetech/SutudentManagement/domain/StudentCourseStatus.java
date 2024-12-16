package raisetech.SutudentManagement.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.SutudentManagement.data.CourseApplication;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseStatus {

  @Valid
  public Student student;

  @Valid
  public List<StudentCourse> studentCourseList;

  @Valid
  public List<CourseApplication> courseApplicationList;
}
