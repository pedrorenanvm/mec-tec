package com.br.edu.ufersa.prog_web.mec_tec.config.errors;

import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerNotFoundException;
import com.br.edu.ufersa.prog_web.mec_tec.machine.exception.MachineNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.order.execption.OrderNotFoundExecption;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceAlreadyExist;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.task.exception.TaskNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserInvalidPasswordException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Um ou mais campos estão inválidos. Verifique os detalhes.",
                request.getRequestURI(),
                validationErrors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            CustomerNotFoundException.class,
            MachineNotFound.class,
            OrderNotFoundExecption.class,
            PieceNotFound.class,
            TaskNotFound.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CustomerAlreadyExistsException.class,
            PieceAlreadyExist.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleConflictExceptions(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Resource Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserInvalidPasswordException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.",
                request.getRequestURI()
        );
        // log.error("Unhandled exception occurred", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}