package com.ims.resthateoas;

public class UserAlreadyExistsException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "User already exists";

  public UserAlreadyExistsException() {
    super(DEFAULT_MESSAGE);
  }

  public UserAlreadyExistsException(String message) {
    super(message);
  }

}
