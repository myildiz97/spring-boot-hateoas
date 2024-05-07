package com.ims.resthateoas;

public class UserNotLoggedInException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "User is not logged in!";

  public UserNotLoggedInException() {
    super(DEFAULT_MESSAGE);
  }

  public UserNotLoggedInException(String message) {
    super(message);
  }

}
