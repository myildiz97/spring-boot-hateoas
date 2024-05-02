package com.ims.resthateoas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class StudentRepository {
  // Mapping of studentId to Student
  private Map<Long, Student> students = new HashMap<>();

  @PostConstruct
  public void init() {
    Student student1 = new Student("John", "Doe");
    System.out.println("StudentRepository.init(): student1: " + student1);
    Student student2 = new Student("Jane", "Doe");
    Student student3 = new Student("Alice", "Smith");
    Student student4 = new Student("Bob", "Smith");

    save(student1);
    save(student2);
    save(student3);
    save(student4);
  }

  public List<Student> findAll() {
    return List.copyOf(students.values());
  }

  public Student findById(Long id) {
    return students.get(id);
  }

  public void deleteById(Long id) {
    students.remove(id);
  }

  public void save(Student student) {
    students.put(student.getStudentId(), student);
  }
}
