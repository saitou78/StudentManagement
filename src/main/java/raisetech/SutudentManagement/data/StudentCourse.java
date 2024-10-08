package raisetech.SutudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

    private String id;
    private String studentInformationId;
    private String courseName;
    private LocalDateTime startDate;
    private LocalDateTime finalDate;
}
