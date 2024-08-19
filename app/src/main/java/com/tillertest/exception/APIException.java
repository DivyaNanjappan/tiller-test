package com.tillertest.exception;

import java.io.IOException;

public class APIException extends IOException {
    private String message;

    public APIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
