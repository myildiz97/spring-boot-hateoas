package com.ims.resthateoas;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SuccessMessage {
  private String message;

  private static final String DEFAULT_MESSAGE = "Success";

  public SuccessMessage() {
    this.message = DEFAULT_MESSAGE;
  }

  public SuccessMessage(String message) {
    this.message = message;
  }
}
