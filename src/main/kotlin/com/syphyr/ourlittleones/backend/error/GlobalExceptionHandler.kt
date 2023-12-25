package com.syphyr.ourlittleones.backend.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception): ResponseEntity<ApiError> {
        val apiError = ex.toApiError()
        return ResponseEntity.status(apiError.status).body(apiError)
    }

}
