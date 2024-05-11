package com.ims.resthateoas;

public class UsersNotRetrievedException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "Users could not be retrieved";

  public UsersNotRetrievedException() {
    super(DEFAULT_MESSAGE);
  }

  public UsersNotRetrievedException(String message) {
    super(message);
  }
}
