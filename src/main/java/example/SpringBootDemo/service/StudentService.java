package example.SpringBootDemo.service;

import example.SpringBootDemo.entity.Student;
import example.SpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq) {
        studentReq.setDeleted(false);
        return studentRepository.save(studentReq);
    }

    public Student getStudent(Long id)
    {
        java.util.Optional<Student> studentRespo =studentRepository.findByIdAndDeletedFalse(id);
        if (studentRespo.isPresent()) {
            return studentRespo.get();
        }return null;
    }
    public List<Student> getAllStudent()
    {
       java.util.List<Student> list =studentRepository.findByDeletedFalse();
       return list;
    }
    public Student updateStudent(Long id, Student studentReq) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedFalse(id);

        if(existingStudent.isEmpty()) {
            return null;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setEmail(studentReq.getEmail());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setDeleted(false);

        return studentRepository.save(studentToSave);
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
}