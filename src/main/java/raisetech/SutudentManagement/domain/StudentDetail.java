package raisetech.SutudentManagement.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.SutudentManagement.data.Student;
import raisetech.SutudentManagement.data.StudentCourse;

@Getter
@Setter
public class StudentDetail {

  public Student student;
  public List<StudentCourse> studentCourse;

}
