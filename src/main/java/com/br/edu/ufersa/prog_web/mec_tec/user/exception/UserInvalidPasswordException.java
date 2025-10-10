package com.br.edu.ufersa.prog_web.mec_tec.user.exception;

public class UserInvalidPasswordException extends RuntimeException {
    public UserInvalidPasswordException(String message) {
        super(message);
    }
}
