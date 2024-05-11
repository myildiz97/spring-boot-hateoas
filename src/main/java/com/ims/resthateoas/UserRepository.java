package com.ims.resthateoas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class UserRepository {
  private Map<Long, User> users = new HashMap<>();

  @PostConstruct
  public void initData() {
    User customer = new User();

    customer.setName("Mehmet");
    customer.setBirthDate("03.08.1997");
    customer.setAccountNumber(19978);
    customer.setBankName("Ziraat Bankası");
    customer.setCompanyName("solarVis");
    customer.setEmail("mehmetyildiz@solarvis.co");
    customer.setGender(User.Gender.MALE);
    customer.setRole(User.UserRoles.CUSTOMER);
    customer.setPassword("mehmet123");
    customer.setPhone("0553 130 95 98");
    customer.setUsername("mehmetyildiz");

    users.put(customer.getId(), customer);

    User supplier = new User();
    supplier.setName("Deniz");
    supplier.setBirthDate("29.05.1996");
    supplier.setAccountNumber(19965);
    supplier.setBankName("Türkiye Ekonomi Bankası");
    supplier.setCompanyName("MilSOFT");
    supplier.setEmail("deniz.unal@metu.edu.tr");
    supplier.setGender(User.Gender.OTHER);
    supplier.setRole(User.UserRoles.SUPPLIER);
    supplier.setPassword("123deniz");
    supplier.setPhone("0538 272 32 54");
    supplier.setUsername("sdenizu");

    users.put(supplier.getId(), supplier);

    User importManager = new User();
    importManager.setName("Umut");
    importManager.setBirthDate("13.08.1998");
    importManager.setAccountNumber(19998);
    importManager.setBankName("İş Bankası");
    importManager.setCompanyName("Roketsan");
    importManager.setEmail("umut.oztop@metu.edu.tr");
    importManager.setGender(User.Gender.MALE);
    importManager.setRole(User.UserRoles.IMPORT_MANAGER);
    importManager.setPassword("umut987");
    importManager.setPhone("0534 348 34 82");
    importManager.setUsername("uoztop");

    users.put(importManager.getId(), importManager);
  }

  public List<User> getAllUsers() {
    return users.values().stream().toList();
  }

  public User findById(Long id) {
    User user = users.get(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return users.get(id);
  }

  public User findByUsername(String username) {
    for (Map.Entry<Long, User> entry : users.entrySet()) {
      if (entry.getValue().getUsername().equals(username)) {
        return entry.getValue();
      }
    }
    throw new UserNotFoundException();
  }

  public void deleteById(Long id) {
    User user = users.get(id);

    if (user == null) {
      throw new UserNotFoundException();
    }
    users.remove(id);
  }

  public boolean save(User user) {
    if (user.getUsername().length() < 3) {
      throw new UsernameTooShortException();
    } else if (user.getPassword().length() < 6) {
      throw new PasswordTooShortException();
    } else {
      Boolean isUserExist = false;
      for (Map.Entry<Long, User> entry : users.entrySet()) {
        if (entry.getValue().getUsername().equals(user.getUsername()) || entry.getKey().equals(user.getId())) {
          isUserExist = true;
          break;
        }
      }
      if (isUserExist) {
        throw new UserAlreadyExistsException();
      } else {
        users.put(user.getId(), user);
        user.setLoggedIn(false);
        return true;
      }
    }
  }

  public boolean login(String username, String password) {
    User existingUser = findByUsername(username);

    if (existingUser == null) {
      throw new UserNotFoundException();
    } else if (!existingUser.getPassword().equals(password)) {
      throw new InvalidPasswordException();
    } else if (!existingUser.getUsername().equals(username)) {
      throw new InvalidUsernameException(username);
    } else {
      existingUser.setLoggedIn(true);
      return true;
    }
  }

  public boolean logout(String username) {
    User existingUser = findByUsername(username);

    if (existingUser == null) {
      throw new UserNotFoundException();
    } else if (!existingUser.getUsername().equals(username)) {
      throw new InvalidUsernameException(username);
    } else if (!existingUser.isLoggedIn()) {
      throw new UserNotLoggedInException();
    } else {
      existingUser.setLoggedIn(false);
      return true;
    }
  }

  public boolean isUserLoggedIn(String username) {
    User existingUser = findByUsername(username);

    if (existingUser == null) {
      throw new UserNotFoundException();
    } else {
      return existingUser.isLoggedIn();
    }
  }

}
