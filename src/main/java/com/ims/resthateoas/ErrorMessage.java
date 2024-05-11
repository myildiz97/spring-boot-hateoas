package com.ims.resthateoas;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ErrorMessage {
  private String message;

  private static final String DEFAULT_MESSAGE = "Error";

  public ErrorMessage() {
    this.message = DEFAULT_MESSAGE;
  }

  public ErrorMessage(String message) {
    this.message = message;
  }
}
