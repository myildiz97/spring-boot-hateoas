package com.ims.resthateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Currency;
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
  @Tag(name = "Users", description = "User operations")
  @Operation(summary = "Get all users", description = "Get all users")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getUsers() {
    try {
      List<User> users = userRepository.getAllUsers();
      for (User user : users) {
        addUserLinks(user);
      }
      return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @GetMapping("/users/{id}")
  @Tag(name = "Users", description = "User operations")
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
      return new ResponseEntity<User>(user, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
    }
  }

  @PostMapping("/users/signup")
  @Tag(name = "Users", description = "User operations")
  @Operation(summary = "Signup a user", description = "Signup a user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> signup(
      @Parameter(name = "user", description = "User object to be created", required = true) @RequestBody User user) {
    try {
      userRepository.save(user);
      addUserLinks(user);
      return new ResponseEntity<User>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @PostMapping("/users/login")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Login a user", description = "Login a user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> login(
      @Parameter(name = "login request", description = "User username and password", required = true) @RequestBody LoginRequest loginRequest) {
    HashMap<String, String> message = new HashMap<>();
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    try {
      User existingUser = userRepository.findByUsername(username);

      Link logoutLink = linkTo(methodOn(UserController.class).userLogout(existingUser.getId())).withRel("logout");
      message.put("logout", logoutLink.getHref());

      userRepository.login(username, password);
      message.put("success", "User logged in successfully");
      return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.OK);
    } catch (Exception e) {
      if (e instanceof UserNotFoundException) {
        return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
      } else if (e instanceof InvalidPasswordException || e instanceof InvalidUsernameException) {
        return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
      } else if (e instanceof AlreadyLoggedInException) {
        User existingUser = userRepository.findByUsername(username);
        Link logoutLink = linkTo(methodOn(UserController.class).userLogout(existingUser.getId())).withRel("logout");
        message.put("logout", logoutLink.getHref());
        message.put("error", e.getMessage());
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @GetMapping("/users/{id}/logout")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Logout a user", description = "Logout a user by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> userLogout(
      @Parameter(name = "id", description = "User id to be logged out", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      HashMap<String, String> message = new HashMap<>();
      LoginRequest loginRequest = new LoginRequest();
      Link loginLink = linkTo(methodOn(UserController.class).login(loginRequest)).withRel("login");
      message.put("login", loginLink.getHref());
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        userRepository.logout(user.getUsername());
        message.put("success", "User logged out successfully");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.OK);
      } else {
        message.put("error", "User is not logged in yet");
        return new ResponseEntity<HashMap<String, String>>(message, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @GetMapping("/users/{id}/loggedin")
  @Tag(name = "Login-Logout", description = "User login logout operations")
  @Operation(summary = "Check if user is logged in", description = "Check if user is logged in by ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) }),
      @ApiResponse(responseCode = "500", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> isUserLoggedIn(
      @Parameter(name = "id", description = "User id to be checked as logged in", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      String status = "";
      if (userRepository.isUserLoggedIn(user.getUsername())) {
        status = "User is logged in";
      } else {
        status = "User is not logged in";
      }
      return new CustomResponse("status", status).getResponse();
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @GetMapping("/users/{id}/bankname")
  @Tag(name = "Users", description = "User operations")
  @Operation(summary = "Get bank name", description = "Get bank name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getBankName(
      @Parameter(description = "User id to be retrieved of bankname ", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      String bankName = user.getBankName();
      return new CustomResponse("bankName", bankName).getResponse();
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
    }
  }

  @GetMapping("/users/{id}/accountnumber")
  @Tag(name = "Users", description = "User operations")
  @Operation(summary = "Get account number", description = "Get account number by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getAccountNumber(
      @Parameter(description = "User id to be retrieved of account number", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      long accountNumber = user.getAccountNumber();
      return new CustomResponse("accountNumber", String.valueOf(accountNumber)).getResponse();
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
    }
  }

  @GetMapping("/users/{id}/companyname")
  @Tag(name = "Users", description = "User operations")
  @Operation(summary = "Get company name", description = "Get company name by user ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  })
  public ResponseEntity<?> getCompanyName(
      @Parameter(description = "User id to be retrieved of company name", required = true) @PathVariable Long id) {
    try {
      User user = userRepository.findById(id);
      String companyName = user.getCompanyName();
      return new CustomResponse("companyName", companyName).getResponse();
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
    }
  }

  
  // @GetMapping("users/gprc/{id}")
  // @Tag(name = "Users", description = "User operations")
  // @Operation(summary = "Get extra info from grpc", description = "Get the extra information for the user from grpc.")
  // @ApiResponses({
  //     @ApiResponse(responseCode = "200", content = {
  //         @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
  //     @ApiResponse(responseCode = "404", content = {
  //         @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)) })
  // })
  // public ResponseEntity<?> getExtraInformation(
  //     @Parameter(description = "User id for extra information", required = true) @PathVariable Long id) {
  //   try {
  //     ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
  //         .usePlaintext()
  //         .build();

  //     UsersService.UsersServiceBlockingStub stub = UsersService.newBlockingStub(channel);
  //     LoginUser request = CurrencyRequest.newBuilder().setId(id).build();
  //     CurrencyResponse response = stub.findOne(request);

  //     Currency currency = new Currency();
  //     currency.setId(response.getCurrency().getId());
  //     currency.setName(response.getCurrency().getName());
  //     currency.setShortName(response.getCurrency().getShortName());

  //     return new CustomResponse("currency", currency).getResponse();
  //   } catch (Exception e) {
  //     return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
  //   }
  // }


  @PutMapping("/users/{id}")
  @Tag(name = "Users", description = "User operations")
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
    try {
      user.setId(id);
      User existingUser = userRepository.update(user);
      addUserLinks(existingUser);
      return new ResponseEntity<User>(existingUser, HttpStatus.OK);
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
    }
  }

  @DeleteMapping("/users/{id}")
  @Tag(name = "Users", description = "User operations")
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
      return new CustomResponse("message", "User deleted successfully").getResponse();
    } catch (Exception e) {
      return new CustomResponse(e.getMessage(), HttpStatus.NOT_FOUND).getResponse();
    }
  }

}
