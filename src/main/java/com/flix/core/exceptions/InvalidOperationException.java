package com.flix.core.exceptions;

import java.io.Serial;

public class InvalidOperationException extends Exception {

  @Serial private static final long serialVersionUID = -5494991695100468597L;

  public InvalidOperationException(String message) {
    super(message);
  }
}
