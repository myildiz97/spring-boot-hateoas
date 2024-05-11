package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
  @Tag(name = "Users", description = "Get all users")
  @Operation(summary = "Get all users", description = "Get all users")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
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
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get a user", description = "Get a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> getUser(
      @Parameter(description = "User id to be retrieved", required = true) @PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      addUserLinks(user);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/users/{id}/bankname")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get bank name", description = "Get bank name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> getBankName(
      @Parameter(description = "User id to be retrieved of bankname ", required = true) @PathVariable Long id) {
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
    return new ResponseEntity<>(bankName, HttpStatus.OK);
  }

  @GetMapping("/users/{id}/accountnumber")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get account number", description = "Get account number by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> getAccountNumber(
      @Parameter(description = "User id to be retrieved of account number", required = true) @PathVariable Long id) {
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
    return new ResponseEntity<>(accountNumber, HttpStatus.OK);
  }

  @GetMapping("/users/{id}/companyname")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get company name", description = "Get company name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> getCompanyName(
      @Parameter(description = "User id to be retrieved of company name", required = true) @PathVariable Long id) {
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
    return new ResponseEntity<>(companyName, HttpStatus.OK);
  }

  @PutMapping("/users/{id}")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Update a user", description = "Update a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> updateUser(
      @Parameter(name = "id", description = "User id to be updated", required = true) @PathVariable Long id,
      @Parameter(name = "user", description = "User object to be updated", required = true) @RequestBody User user) {
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
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Delete a user", description = "Delete a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> deleteUser(
      @Parameter(name = "id", description = "User id to be deleted", required = true) @PathVariable Long id) {
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

  @PostMapping("/users/signup")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Signup a user", description = "Signup a user")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "409", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> signup(
      @Parameter(name = "user", description = "User object to be created", required = true) @RequestBody User user) {
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

  @PostMapping("/users/login")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Login a user", description = "Login a user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> login(
      @Parameter(name = "login request", description = "User username and password", required = true) @RequestBody LoginRequest loginRequest) {
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
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Logout a user", description = "Logout a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> userLogout(
      @Parameter(name = "id", description = "User id to be logged out", required = true) @PathVariable Long id) {
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
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Check if user is logged in", description = "Check if user is logged in by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)) })
  })
  public ResponseEntity<?> isUserLoggedIn(
      @Parameter(name = "id", description = "User id to be checked as logged in", required = true) @PathVariable Long id) {
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

}
