package com.marco.scmexc.handlers.exception

import com.marco.scmexc.models.exceptions.user.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun handleUserNotFoundException() : ResponseEntity<Any> = ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("User not found.")

    @ExceptionHandler(value = [InvalidUserAuthenticationException::class])
    fun handleInvalidUserAuthentication() = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Invalid user authentication.")

    @ExceptionHandler(value = [NotAdminException::class])
    fun handleNotAdminException() = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Not an Admin")

    @ExceptionHandler(value = [InvalidNewUserInfoException::class])
    fun handleInvalidNewUserInfoException() = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Invalid user info.")

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun handleUserAlreadyExistsException() = ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("User already exists.")

    @ExceptionHandler(value = [NoPermissionException::class])
    fun handleNoPermissionException() = ResponseEntity.status((HttpStatus.UNAUTHORIZED))
            .body(("Not enough permissions."))

    @ExceptionHandler(value = [InvalidPasswordException::class])
    fun handleInvalidPasswordException() = ResponseEntity.status((HttpStatus.BAD_REQUEST))
        .body(("You have entered an invalid password."))

    @ExceptionHandler(value = [EmailAlreadyExistsException::class])
    fun handleEmailAlreadyExistsException() = ResponseEntity.status((HttpStatus.BAD_REQUEST))
        .body(("That email address already exists."))
}
