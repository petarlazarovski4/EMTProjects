package com.marco.scmexc.handlers.exception

import com.marco.scmexc.handlers.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AccessDeniedExceptionHandler {

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDeniedException() : ResponseEntity<Any> = ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Access denied.")

}
