package in.novopay.wallet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?>
    defaultErrorHandler(WebRequest req, Exception e) throws Exception {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}