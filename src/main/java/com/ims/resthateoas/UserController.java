package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
    Long userId = user.getId();
    user.removeLinks();

    user.add(linkTo(methodOn(UserController.class).getUser(userId)).withSelfRel());
    user.add(linkTo(methodOn(UserController.class).getBankName(userId)).withRel("bankName"));
    user.add(linkTo(methodOn(UserController.class).getAccountNumber(userId)).withRel("accountNumber"));
    user.add(linkTo(methodOn(UserController.class).getCompanyName(userId)).withRel("companyName"));

    Boolean loggedIn = userRepository.isUserLoggedIn(user.getUsername());
    if (!loggedIn) {
      LoginRequest loginRequest = new LoginRequest();
      user.add(linkTo(methodOn(UserController.class).login(loginRequest)).withRel("login"));
    } else {
      user.add(linkTo(methodOn(UserController.class).userLogout(userId)).withRel("logout"));
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
      addUserLinks(user);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new HttpEntity<>(user);
  }

  @GetMapping("/users/{id}/bankname")
  public HttpEntity<?> getBankName(@PathVariable Long id) {
    String bankName;
    User user;
    try {
      user = userRepository.findById(id);
      bankName = user.getBankName();
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new HttpEntity<>(bankName);
  }

  @GetMapping("/users/{id}/accountnumber")
  public HttpEntity<?> getAccountNumber(@PathVariable Long id) {
    long accountNumber;
    User user;
    try {
      user = userRepository.findById(id);
      accountNumber = user.getAccountNumber();
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new HttpEntity<>(accountNumber);
  }

  @GetMapping("/users/{id}/companyname")
  public HttpEntity<?> getCompanyName(@PathVariable Long id) {
    String companyName;
    User user;
    try {
      user = userRepository.findById(id);
      companyName = user.getCompanyName();
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new HttpEntity<>(companyName);
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

  @PostMapping("/users/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    User existingUser;
    try {
      userRepository.login(loginRequest.getUsername(), loginRequest.getPassword());
      existingUser = userRepository.findByUsername(loginRequest.getUsername());
      addUserLinks(existingUser);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(existingUser, HttpStatus.OK);
  }

  @GetMapping("/users/{id}/logout")
  public ResponseEntity<?> userLogout(@PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        userRepository.logout(user.getUsername());
        Map<String, String> success = new HashMap<>();
        success.put("success", "User logged out successfully");
        LoginRequest loginRequest = new LoginRequest();
        Link loginLink = linkTo(methodOn(UserController.class).login(loginRequest)).withRel("login");
        success.put("login", loginLink.getHref()); // Use getHref() to get the URI as a string
        return new ResponseEntity<Map<String, String>>(success, HttpStatus.OK);
      }
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.UNAUTHORIZED);
    }
    Map<String, String> message = new HashMap<>();
    message.put("message", "User is not logged in");
    LoginRequest loginRequest = new LoginRequest();
    Link loginLink = linkTo(methodOn(UserController.class).login(loginRequest)).withRel("login");
    message.put("login", loginLink.getHref()); // Use getHref() to get the URI as a string
    return new ResponseEntity<Map<String, String>>(message, HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/users/{id}/loggedin")
  public ResponseEntity<?> isUserLoggedIn(@PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        Map<String, String> message = new HashMap<>();
        message.put("message", "User is logged in");
        return new ResponseEntity<Map<String, String>>(message, HttpStatus.OK);
      }
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.UNAUTHORIZED);
    }
    Map<String, String> message = new HashMap<>();
    message.put("message", "User is not logged in");
    return new ResponseEntity<Map<String, String>>(message, HttpStatus.UNAUTHORIZED);
  }

  @PostMapping("/users/signup")
  public ResponseEntity<?> signup(@RequestBody User user) {
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

}
