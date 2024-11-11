package raisetech.SutudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Pattern(regexp = "^\\d+$", message = "数字のみ入力してください")
  private String id;

  @NotBlank(message = "名前を入力してください")
  private String name;

  @NotBlank(message = "フリガナを入力してください")
  private String frigana;

  private String nickname;

  @NotBlank(message = "メールアドレスを入力してください")
  private String email;

  @NotBlank(message = "地域を入力してください")
  private String address;

  private int age;

  @NotBlank(message = "性別を入力してください")
  private String gender;

  private String remark;

  private boolean isDeleted;
}
