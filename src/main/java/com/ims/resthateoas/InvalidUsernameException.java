package com.ims.resthateoas;

public class InvalidUsernameException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "Invalid username";

  public InvalidUsernameException() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidUsernameException(String username) {
    super("Invalid username: " + username);
  }

}
