package com.ims.resthateoas;

public class AlreadyLoggedInException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "User is already logged in";

  public AlreadyLoggedInException() {
    super(DEFAULT_MESSAGE);
  }

  public AlreadyLoggedInException(String username) {
    super("User is already logged in: " + username);
  }

}
