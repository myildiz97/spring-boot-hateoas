package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}