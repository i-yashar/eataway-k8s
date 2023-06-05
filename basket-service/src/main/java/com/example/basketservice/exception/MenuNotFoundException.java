package com.example.basketservice.exception;

import java.io.Serial;

public class MenuNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7577015163922215482L;

    public MenuNotFoundException(String message) {
        super(message);
    }
}
