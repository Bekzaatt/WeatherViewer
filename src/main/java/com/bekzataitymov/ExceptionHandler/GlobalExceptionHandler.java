package com.bekzataitymov.ExceptionHandler;

import com.bekzataitymov.Service.Interface.LocationService;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(HttpClientErrorException.class)
//    public ResponseEntity<String> handleClientError(HttpClientErrorException ex) {
//        HttpStatus httpStatus = (HttpStatus) ex.getStatusCode();
//        String msg =ex.getMessage();
//        return new ResponseEntity<>(msg, httpStatus);
//    }
//
//    @ExceptionHandler(HttpClientErrorException.NotFound.class)
//    public ResponseEntity<String> handleClientError(HttpClientErrorException.NotFound ex) {
//        HttpStatus httpStatus = (HttpStatus) ex.getStatusCode();
//        String msg =ex.getMessage();
//        return new ResponseEntity<>("City Not found", httpStatus);
//    }

//    @ExceptionHandler(NonUniqueResultException.class)
//    public ResponseEntity<String> handleNonUniqueResultException(NonUniqueResultException ex){
//        return new ResponseEntity<>("There's two or more same locations", HttpStatus.BAD_REQUEST);
//    }


}
