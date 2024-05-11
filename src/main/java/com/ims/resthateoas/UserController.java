package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getUsers() {
    try {
      List<User> users = new ArrayList<>();
      users = userRepository.getAllUsers();
      for (User user : users) {
        addUserLinks(user);
      }
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).createResponse();
    }
  }

  @GetMapping("/users/{id}")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get a user", description = "Get a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))
      })
  })
  public ResponseEntity<?> getUser(
      @Parameter(description = "User id to be retrieved", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      addUserLinks(user);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new Response(e.getMessage(), HttpStatus.NOT_FOUND).createResponse();
    }
  }

  @GetMapping("/users/{id}/bankname")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get bank name", description = "Get bank name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getBankName(
      @Parameter(description = "User id to be retrieved of bankname ", required = true) @PathVariable Long id) {
    String bankName;
    User user;
    try {
      user = userRepository.findById(id);
      bankName = user.getBankName();
      return new Response("bankName", bankName).createResponse();
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.NOT_FOUND).createResponse();
    }
  }

  @GetMapping("/users/{id}/accountnumber")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get account number", description = "Get account number by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getAccountNumber(
      @Parameter(description = "User id to be retrieved of account number", required = true) @PathVariable Long id) {
    long accountNumber;
    User user;
    try {
      user = userRepository.findById(id);
      accountNumber = user.getAccountNumber();
      return new Response("accountNumber", String.valueOf(accountNumber)).createResponse();
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.NOT_FOUND).createResponse();
    }
  }

  @GetMapping("/users/{id}/companyname")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Get company name", description = "Get company name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getCompanyName(
      @Parameter(description = "User id to be retrieved of company name", required = true) @PathVariable Long id) {
    String companyName;
    User user;
    try {
      user = userRepository.findById(id);
      companyName = user.getCompanyName();
      return new Response("companyName", companyName).createResponse();
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).createResponse();
    }
  }

  @PutMapping("/users/{id}")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Update a user", description = "Update a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
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
      return new ResponseEntity<>(existingUser, HttpStatus.OK);
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).createResponse();
    }
  }

  @DeleteMapping("/users/{id}")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Delete a user", description = "Delete a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> deleteUser(
      @Parameter(name = "id", description = "User id to be deleted", required = true) @PathVariable Long id) {
    try {
      userRepository.deleteById(id);
      return new Response("message", "User deleted successfully").createResponse();
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.NOT_FOUND).createResponse();
    }
  }

  @PostMapping("/users/signup")
  @Tag(name = "Users", description = "Operations on a User")
  @Operation(summary = "Signup a user", description = "Signup a user")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "409", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> signup(
      @Parameter(name = "user", description = "User object to be created", required = true) @RequestBody User user) {
    try {
      User newUser = new User(user);
      userRepository.save(newUser);
      addUserLinks(newUser);
      return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.CONFLICT).createResponse();
    }
  }

  @PostMapping("/users/login")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Login a user", description = "Login a user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> login(
      @Parameter(name = "login request", description = "User username and password", required = true) @RequestBody LoginRequest loginRequest) {
    try {
      String username = loginRequest.getUsername();
      String password = loginRequest.getPassword();
      User existingUser = userRepository.findByUsername(username);

      HashMap<String, String> message = new HashMap<>();
      Link logoutLink = linkTo(methodOn(UserController.class).userLogout(existingUser.getId())).withRel("logout");
      message.put("logout", logoutLink.getHref());

      if (userRepository.isUserLoggedIn(username)) {
        message.put("error", "User is already logged in");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.UNAUTHORIZED);
      } else {
        userRepository.login(username, password);
        message.put("success", "User logged in successfully");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.OK);
      }
    } catch (Exception e) {
      if (e instanceof UserNotFoundException) {
        return new Response(e.getMessage(), HttpStatus.NOT_FOUND).createResponse();
      } else if (e instanceof InvalidPasswordException) {
        return new Response(e.getMessage(), HttpStatus.UNAUTHORIZED).createResponse();
      } else if (e instanceof InvalidUsernameException) {
        return new Response(e.getMessage(), HttpStatus.UNAUTHORIZED).createResponse();
      }
      return new Response(e.getMessage(), HttpStatus.UNAUTHORIZED).createResponse();
    }
  }

  @GetMapping("/users/{id}/logout")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Logout a user", description = "Logout a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> userLogout(
      @Parameter(name = "id", description = "User id to be logged out", required = true) @PathVariable Long id) {
    try {
      User user;
      user = userRepository.findById(id);
      HashMap<String, String> message = new HashMap<>();
      LoginRequest loginRequest = new LoginRequest();
      Link loginLink = linkTo(methodOn(UserController.class).login(loginRequest)).withRel("login");
      message.put("login", loginLink.getHref()); // Use getHref() to get the URI as a stri
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        userRepository.logout(user.getUsername());
        message.put("success", "User logged out successfully");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.OK);
      } else {
        message.put("error", "User is not logged in yet");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.UNAUTHORIZED).createResponse();
    }

  }

  @GetMapping("/users/{id}/loggedin")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Check if user is logged in", description = "Check if user is logged in by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> isUserLoggedIn(
      @Parameter(name = "id", description = "User id to be checked as logged in", required = true) @PathVariable Long id) {
    User user;
    try {
      user = userRepository.findById(id);
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        return new Response("success", "User is logged in").createResponse();
      } else {
        return new Response("error", "User is not logged in").createResponse();
      }
    } catch (Exception e) {
      return new Response(e.getMessage(), HttpStatus.UNAUTHORIZED).createResponse();
    }
  }

}
