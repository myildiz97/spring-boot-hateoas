package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class UserController {

  @Autowired
  private UserRepository userRepository;

  public User addUserLinks(User user) {
    if (user.getLinks().isEmpty()) {
      user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
    }
    return user;
  }

  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    List<User> users = new ArrayList<>();
    try {
      users = userRepository.findAll();
      for (User user : users) {
        addUserLinks(user);
      }
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/users/{id}")
  public HttpEntity<?> getUser(@PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      user = addUserLinks(user);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new HttpEntity<>(user);
  }

  @PostMapping("/users")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    User newUser = new User(user);
    try {
      userRepository.save(newUser);
      addUserLinks(newUser);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.CONFLICT);
    }
    return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
    User existingUser;
    try {
      existingUser = userRepository.findById(id);
      existingUser.setName(user.getName());
      existingUser.setBirthDate(user.getBirthDate());
      existingUser.setGender(user.getGender());
      existingUser.setRole(user.getRole());
      existingUser.setUsername(user.getUsername());
      existingUser.setEmail(user.getEmail());
      existingUser.setPassword(user.getPassword());
      existingUser.setPhone(user.getPhone());
      existingUser.setAccountNumber(user.getAccountNumber());
      existingUser.setBankName(user.getBankName());
      existingUser.setCompanyName(user.getCompanyName());

      userRepository.save(existingUser);
      addUserLinks(existingUser);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(existingUser, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      userRepository.deleteById(id);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    Map<String, String> success = new HashMap<>();
    success.put("success", "User deleted successfully");
    return new ResponseEntity<Map<String, String>>(success, HttpStatus.OK);
  }

}
