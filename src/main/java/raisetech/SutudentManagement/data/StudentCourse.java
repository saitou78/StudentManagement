package raisetech.SutudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String id;

    private String studentInformationId;

    @NotBlank
    private String courseName;

    private LocalDateTime startDate;

    private LocalDateTime finalDate;
}
