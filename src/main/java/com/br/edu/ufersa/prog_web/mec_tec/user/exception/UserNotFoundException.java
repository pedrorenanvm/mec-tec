package com.br.edu.ufersa.prog_web.mec_tec.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
