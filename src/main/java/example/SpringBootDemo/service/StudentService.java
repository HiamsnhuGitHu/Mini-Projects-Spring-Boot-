package example.SpringBootDemo.service;

import example.SpringBootDemo.DTO.CreateStudentRequestDTO;
import example.SpringBootDemo.DTO.CreateStudentResponseDTO;
import example.SpringBootDemo.DTO.UpdateStudentRequestDTO;
import example.SpringBootDemo.DTO.UpdateStudentResponseDTO;
import example.SpringBootDemo.entity.Student;
import example.SpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDTO) {
        Student student = mapToEntity(createStudentRequestDTO);

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentRespo=studentRepository.save(student);
        return mapToDTO(studentRespo);
    }

    public CreateStudentResponseDTO getStudent(Long id)
    {
        java.util.Optional<Student> studentRespo =studentRepository.findByIdAndDeletedFalse(id);
        if (studentRespo.isPresent()) {
            return mapToDTO(studentRespo.get());
        }return null;
    }
    public List<Student> getAllStudent()
    {
       java.util.List<Student> list =studentRepository.findByDeletedFalse();
       return list;
    }
    public UpdateStudentResponseDTO updateStudent(Long id, UpdateStudentRequestDTO studentReq) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedFalse(id);

        if(existingStudent.isEmpty()) {
            return null;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setDeleted(false);
        studentToSave.setUpdatedAt(LocalDateTime.now());

        Student savedStudent=studentRepository.save(studentToSave);
        return mapToUpdateDto(savedStudent);
    }

    public Boolean deleteStudent(Long id) {
        Boolean isStudent = studentRepository.existsById(id);

        if(!isStudent) return false;

        studentRepository.deleteById(id);

        return true;
    }
    public boolean deleteStudentSoftly(Long id)
    {
        Optional<Student> exitStudent=studentRepository.findByIdAndDeletedFalse(id);
        if(exitStudent.isEmpty()) {
            return  false;
        }
        Student studentToSave=exitStudent.get();
        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);
        return true;
    }

    private Student mapToEntity(CreateStudentRequestDTO createStudentRequestDTO)
    {
        Student s1 = new Student();
        s1.setName(createStudentRequestDTO.getName());
        s1.setAge(createStudentRequestDTO.getAge());
        s1.setEmail(createStudentRequestDTO.getEmail());
        s1.setRollNo(createStudentRequestDTO.getRollNo());
        s1.setSubject(createStudentRequestDTO.getSubject());
        s1.setDeleted(false);
        return s1;
    }

    private CreateStudentResponseDTO mapToDTO(Student student)
    {
        CreateStudentResponseDTO createStudentResponseDTO = new CreateStudentResponseDTO();
        createStudentResponseDTO.setId(student.getId());
        createStudentResponseDTO.setName(student.getName());
        createStudentResponseDTO.setAge(student.getAge());
        createStudentResponseDTO.setEmail(student.getEmail());
        createStudentResponseDTO.setRollNo(student.getRollNo());
        createStudentResponseDTO.setSubject(student.getSubject());
        createStudentResponseDTO.setMassage("student set succesfully");
        createStudentResponseDTO.setCreatedAt(student.getCreatedAt());
        createStudentResponseDTO.setUpdatedAt(student.getUpdatedAt());
        return createStudentResponseDTO;
    }

    private UpdateStudentResponseDTO mapToUpdateDto(Student student) {
        UpdateStudentResponseDTO responseDto = new UpdateStudentResponseDTO();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setMassage("Student updated successfully");
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }


}