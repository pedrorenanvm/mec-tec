package com.br.edu.ufersa.prog_web.mec_tec.machine.exception;

public class MachineNotFound extends RuntimeException{
    public MachineNotFound(String message) {
        super(message);
    }
}
