package banking_api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;



@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex
    ) {


        ErrorResponse error =
                new ErrorResponse(
                        ex.getMessage(),
                        404,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }





    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex
    ) {


        ErrorResponse error =
                new ErrorResponse(
                        ex.getMessage(),
                        400,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex
    ) {


        String message =
                ex.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage();



        ErrorResponse error =
                new ErrorResponse(
                        message,
                        400,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex
    ) {


        ErrorResponse error =
                new ErrorResponse(
                        ex.getMessage(),
                        500,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}