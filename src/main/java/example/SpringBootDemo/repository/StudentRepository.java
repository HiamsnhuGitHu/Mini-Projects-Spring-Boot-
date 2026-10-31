package example.SpringBootDemo.repository;

import example.SpringBootDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public//@Component
interface StudentRepository extends JpaRepository<Student,Long>
{
    Optional<Student> findByIdAndDeletedFalse(Long id);
    List<Student> findByDeletedFalse();
}