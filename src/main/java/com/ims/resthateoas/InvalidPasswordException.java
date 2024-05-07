package com.ims.resthateoas;

public class InvalidPasswordException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "Invalid password";

  public InvalidPasswordException() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidPasswordException(String message) {
    super(message);
  }

}
