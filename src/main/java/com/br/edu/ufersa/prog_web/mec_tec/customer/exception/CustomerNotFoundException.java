package com.br.edu.ufersa.prog_web.mec_tec.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
