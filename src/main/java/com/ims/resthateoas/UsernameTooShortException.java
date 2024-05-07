package com.ims.resthateoas;

public class UsernameTooShortException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "Username must be minimum 3 characters long";

  public UsernameTooShortException() {
    super(DEFAULT_MESSAGE);
  }

  public UsernameTooShortException(String message) {
    super(message);
  }

}
