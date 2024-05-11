package com.ims.resthateoas;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ims_users")
public class User extends RepresentationModel<User> {
  private static final AtomicLong counter = new AtomicLong();

  public enum UserRoles {
    CUSTOMER, SUPPLIER, IMPORT_MANAGER, ADMIN
  }

  public enum Gender {
    MALE, FEMALE, OTHER
  }

  @Id
  @NotNull
  private Long id;
  private String name;
  private String birthDate;
  private Gender gender;
  private UserRoles role;
  @Size(min = 3, max = 20)
  private String username;
  private String email;
  @Size(min = 6, max = 20)
  private String password;
  private String phone;
  private String bankName;
  private long accountNumber;
  private String companyName;
  private boolean loggedIn;

  public User() {
    this.id = counter.incrementAndGet();
    this.loggedIn = false;
  }

  public User(String name, String birthDate, Gender gender, UserRoles role, String username, String email,
      String password, String phone, String bankName, long accountNumber, String companyName, boolean loggedIn) {
    this.id = counter.incrementAndGet();
    this.name = name;
    this.birthDate = birthDate;
    this.gender = gender;
    this.role = role;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.bankName = bankName;
    this.accountNumber = accountNumber;
    this.companyName = companyName;
    this.loggedIn = loggedIn;
  }

  public User(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.birthDate = user.getBirthDate();
    this.gender = user.getGender();
    this.role = user.getRole();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.phone = user.getPhone();
    this.bankName = user.getBankName();
    this.accountNumber = user.getAccountNumber();
    this.companyName = user.getCompanyName();
    this.loggedIn = user.isLoggedIn();
  }

  public boolean isLoggedIn() {
    return this.loggedIn;
  }

}