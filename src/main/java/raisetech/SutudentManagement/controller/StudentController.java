package raisetech.SutudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.SutudentManagement.TestException;
import raisetech.SutudentManagement.domain.StudentCourseStatus;
import raisetech.SutudentManagement.domain.StudentDetail;
import raisetech.SutudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実装されるControllerです。
 */
@RestController
@Validated
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索 全権検索、条件指定なし
   *
   * @return　受講生一覧（全件）
   */
  @Operation(tags = "検索", summary = "一覧検索", description = "受講生の一覧を検索")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索 IDに紐づく任意の受講生の情報を取得
   *
   * @param id 　受講生ID
   * @return　受講生
   */
  @Operation(tags = "検索", summary = "受講生詳細検索", description = "IDに紐づく受講生情報の検索")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 申し込み状況詳細の全権検索検索
   *
   * @return 申し込み状況
   */
  @Operation(tags = "検索", summary = "申し込み状況詳細", description = "申し込み状況詳細の一覧検索")
  @GetMapping("/studentCourseStatusList")
  public List<StudentCourseStatus> getStudentCourseStatusList() {
    return service.searchStudentCourseStatusList();
  }

  /**
   * 申し込み状況詳細の検索　IDに紐づく任意の受講生の情報を取得
   *
   * @param id　受講生ID
   * @return　申し込み状況詳細
   */
  @Operation(tags = "検索", summary = "申し込み状況詳細", description = "受講生IDに紐づく受講生コース情報、申し込み状況の検索")
  @GetMapping("/studentCourseStatus/{id}")
  public List<StudentCourseStatus> getStudentCourseStatuses (@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudentCourseStatus(id);
  }

  /**
   * 受講生詳細の登録
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(tags = "登録", summary = "受講生詳細登録", description = "受講生の登録",
      responses = {@ApiResponse(responseCode = "200", description = "受講生の詳細を一覧で出す")
      })
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 申し込み状況詳細の登録
   *
   * @param studentCourseStatus　申し込み状況詳細
   * @return　実行結果
   */
  @Operation(tags = "登録", summary = "申し込み状況詳細登録", description = "受講生の登録",
      responses = {@ApiResponse(responseCode = "200", description = "申し込み状況の詳細を一覧で出す")
      })
  @PostMapping("/registerStudentCourseStatus")
  public ResponseEntity<StudentCourseStatus> registerStudentCourseStatus(
      @RequestBody @Valid StudentCourseStatus studentCourseStatus) {
    StudentCourseStatus responseStudentCourseStatus = service.registerStudentCourseStatus(studentCourseStatus);
    return ResponseEntity.ok(responseStudentCourseStatus);
  }

  /**
   * 受講生詳細の更新 キャンセルフラグの更新もここで行う
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(tags = "更新", summary = "受講生詳細の更新", description = "キャンセルフラグの更新もできる",
      responses = {@ApiResponse(responseCode = "200", description = "更新処理が成功しました。")
      })
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 申し込み状況詳細の更新
   *
   * @param studentCourseStatus　申し込み状況詳細
   * @return　実行結果
   */
  @Operation(tags = "更新", summary = "申し込み状況詳細の更新", description = "キャンセルフラグの更新もできる",
      responses = {@ApiResponse(responseCode = "200", description = "更新処理が成功しました。")
      })
  @PutMapping("/updateStudentCourseStatus")
  public ResponseEntity<String> updateStudentCourseStatus(@RequestBody @Valid StudentCourseStatus studentCourseStatus) {
    service.updateStudentCourseStatus(studentCourseStatus);
    return ResponseEntity.ok("更新処理が成功しました");
  }

  @Operation(tags = "エラー処理", summary = "エラー処理",
      responses = {@ApiResponse(responseCode = "200", description = "エラーが発生しました")
      })
  @GetMapping("/studentException")
  public List<StudentDetail> getException() throws TestException {
    throw new TestException("エラーが発生しました");
  }
}
