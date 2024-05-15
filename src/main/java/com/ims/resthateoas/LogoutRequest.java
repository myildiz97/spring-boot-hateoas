package com.ims.resthateoas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
  private String username;

  @Override
  public String toString() {
    return "LogoutRequest [username=" + username + "]";
  }
}
