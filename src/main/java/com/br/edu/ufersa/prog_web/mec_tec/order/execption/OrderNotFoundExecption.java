package com.br.edu.ufersa.prog_web.mec_tec.order.execption;

public class OrderNotFoundExecption extends RuntimeException {
    public OrderNotFoundExecption(String message) {
        super(message);
    }
}
