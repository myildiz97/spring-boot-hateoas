package com.ims.resthateoas;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class LoginRequest {
  private String username;
  private String password;

  @Override
  public String toString() {
    return "LoginRequest [username=" + username + ", password=" + password + "]";
  }
}
