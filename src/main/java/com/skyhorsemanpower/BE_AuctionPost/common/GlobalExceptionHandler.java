package com.skyhorsemanpower.BE_AuctionPost.common;

import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        ResponseStatus responseStatus = ex.getResponseStatus();
        return new ExceptionResponse(responseStatus);
    }
}
