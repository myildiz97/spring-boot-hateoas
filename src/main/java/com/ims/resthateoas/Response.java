package com.ims.resthateoas;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
  private HashMap<String, String> mapping;
  private String message;
  private String messageKey;
  private static final String DEFAULT_MESSAGE_KEY = "error";
  private HttpStatus status = HttpStatus.OK;
  private ResponseEntity<HashMap<String, String>> response;

  public Response() {
    this.mapping = new HashMap<>();
    this.response = new ResponseEntity<>(mapping, status);
  }

  public Response(String message) {
    this();
    this.messageKey = DEFAULT_MESSAGE_KEY;
    this.message = message;
    this.mapping.put(messageKey, message);
  }

  public Response(String message, HttpStatus status) {
    this();
    this.messageKey = DEFAULT_MESSAGE_KEY;
    this.message = message;
    this.mapping.put(messageKey, message);
    this.status = status;
  }

  public Response(String messageKey, String message) {
    this();
    this.messageKey = messageKey;
    this.message = message;
    this.mapping.put(messageKey, message);
  }

  public Response(String messageKey, String message, HttpStatus status) {
    this(messageKey, message);
    this.status = status;
  }

  public ResponseEntity<HashMap<String, String>> createResponse() {
    return new ResponseEntity<>(mapping, status);
  }
}