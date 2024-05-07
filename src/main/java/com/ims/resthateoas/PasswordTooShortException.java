package com.ims.resthateoas;

public class PasswordTooShortException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "Password must be minimum 6 characters long";

  public PasswordTooShortException() {
    super(DEFAULT_MESSAGE);
  }

  public PasswordTooShortException(String message) {
    super(message);
  }

}
