package com.ims.resthateoas;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student extends RepresentationModel<Student> {
  private static final AtomicLong counter = new AtomicLong();

  @Id
  private Long studentId;
  private String firstName;
  private String lastName;

  public Student() {
  }

  public Student(String firstName, String lastName) {
    this.studentId = counter.incrementAndGet();
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
