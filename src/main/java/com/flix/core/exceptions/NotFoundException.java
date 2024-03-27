package com.flix.core.exceptions;

import java.io.Serial;

public class NotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = -5494001695109468597L;

    public NotFoundException(String message) {
        super(message);
    }
}
