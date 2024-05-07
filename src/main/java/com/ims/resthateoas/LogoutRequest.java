package com.ims.resthateoas;

public class LogoutRequest {
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "LogoutRequest [username=" + username + "]";
  }
}
