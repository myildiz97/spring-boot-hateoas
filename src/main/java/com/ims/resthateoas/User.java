package com.ims.resthateoas;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
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
  private Long id;
  private String name;
  private String birthDate;
  private Gender gender;
  private UserRoles role;
  private String username;
  private String email;
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

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthDate() {
    return this.birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public Gender getGender() {
    return this.gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public UserRoles getRole() {
    return this.role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getBankName() {
    return this.bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public long getAccountNumber() {
    return this.accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public boolean isLoggedIn() {
    return this.loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

}