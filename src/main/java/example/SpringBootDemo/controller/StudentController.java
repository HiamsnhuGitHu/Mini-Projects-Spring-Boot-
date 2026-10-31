package example.SpringBootDemo.controller;

import example.SpringBootDemo.DTO.UpdateStudentResponseDTO;

import example.SpringBootDemo.DTO.CreateStudentRequestDTO;
import example.SpringBootDemo.DTO.CreateStudentResponseDTO;
import example.SpringBootDemo.DTO.UpdateStudentRequestDTO;
import example.SpringBootDemo.entity.Student;
import example.SpringBootDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateStudentResponseDTO> createStudent(@Valid @RequestBody CreateStudentRequestDTO createStudentRequestDTO) {

        CreateStudentResponseDTO createdStudent =
                studentService.createStudent(createStudentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<CreateStudentResponseDTO> getStudent(@RequestParam Long id)
    {
        CreateStudentResponseDTO s1 =studentService.getStudent(id);
        if(s1==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok()
                .body(s1);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent()
    {
        List<Student> s2 =studentService.getAllStudent();
        if(s2==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(s2);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateStudentResponseDTO> updateStudent(@RequestParam Long id,
                                                                  @RequestBody UpdateStudentRequestDTO updateStudentRequestDTO) {
        UpdateStudentResponseDTO studentResp = studentService.updateStudent(id, updateStudentRequestDTO);

        if(studentResp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        Boolean isDeleted = studentService.deleteStudent(id);

        if(!isDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Record deleted");
    }
    @PatchMapping("delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id)
    {
        boolean isDeleted=studentService.deleteStudentSoftly(id);
        if(isDeleted)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record Deleted");
    }

}