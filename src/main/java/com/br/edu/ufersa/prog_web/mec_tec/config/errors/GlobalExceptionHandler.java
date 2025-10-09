package com.br.edu.ufersa.prog_web.mec_tec.config.errors;
import com.br.edu.ufersa.prog_web.mec_tec.order.execption.OrderNotFoundExecption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundExecption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleOrderNotFound(OrderNotFoundExecption ex) {
        return Map.of("error", ex.getMessage());
    }
}