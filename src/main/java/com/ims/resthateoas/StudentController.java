package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/students")
  public ResponseEntity<List<Student>> getStudents() {
    List<Student> students = studentRepository.findAll();
    for (Student student : students) {
      student.add(linkTo(methodOn(StudentController.class).getStudent(student.getStudentId())).withSelfRel());
    }
    return new ResponseEntity<>(students, HttpStatus.OK);
  }

  @GetMapping("/students/{id}")
  public HttpEntity<Student> getStudent(@PathVariable Long id) {
    Student student = studentRepository.findById(id);
    student.add(linkTo(methodOn(StudentController.class).getStudent(id)).withSelfRel());
    return new HttpEntity<>(student);
  }

  @PostMapping("/students")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student newStudent = new Student(student.getFirstName(), student.getLastName());
    studentRepository.save(newStudent);
    student.add(linkTo(methodOn(StudentController.class).getStudent(student.getStudentId())).withSelfRel());
    return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
  }

  @PutMapping("/students/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
    Student existingStudent = studentRepository.findById(id);
    if (existingStudent == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    existingStudent.setFirstName(student.getFirstName());
    existingStudent.setLastName(student.getLastName());
    studentRepository.save(existingStudent);
    existingStudent.add(linkTo(methodOn(StudentController.class).getStudent(id)).withSelfRel());
    return new ResponseEntity<>(existingStudent, HttpStatus.OK);
  }

  @DeleteMapping("/students/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    Student student = studentRepository.findById(id);
    if (student == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    studentRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}