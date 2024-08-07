package com.example.speedotansfer.exception;

import com.example.speedotansfer.exception.custom.*;
import com.example.speedotansfer.exception.response.ErrorDetails;
import com.example.speedotansfer.exception.response.ValidationFailedResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> customerAlreadyExistExceptionHandling(UserAlreadyExistsException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundExceptionHandling(UserNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandling(MethodArgumentNotValidException e) {
        String details = "";

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ValidationFailedResponse error = ValidationFailedResponse
                .builder()
                .message("Validation failed for one or more fields")
                .details(errors.toString())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e) {

        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        ValidationFailedResponse error = ValidationFailedResponse
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Validation failed for one or more fields")
                .details(errors.toString())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandling(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now()
                , exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationErrorException.class)
    public ResponseEntity<Object> authenticationExceptionHandling(AuthenticationErrorException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now()
                , String.format("Incorrect email or password credentials provided. [ %s ]", exception.getMessage()),
                request.getDescription(false), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<Object> InsufficientAmountExceptionHandling(InsufficientAmountException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> dataIntegrityViolationExceptionHandling(DataIntegrityViolationException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> accountNotFoundExceptionHandler(AccountNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<Object> invalidTransferExceptionHandler(InvalidTransferException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> invalidJwtTokenExceptionHandler(InvalidJwtTokenException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FavouriteNotFoundException.class)
    public ResponseEntity<Object> favouriteNotFoundExceptionHandler(FavouriteNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountAlreadyExists.class)
    public ResponseEntity<Object> accountAlreadyExistsHandler(AccountAlreadyExists exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundExceptionHandler(UsernameNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}

