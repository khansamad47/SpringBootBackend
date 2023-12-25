package com.syphyr.ourlittleones.backend.error

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException

fun Exception.toApiError(): ApiError {
    val apiError = when (this) {
        is BadCredentialsException -> ApiError(HttpStatus.UNAUTHORIZED, "Incorrect Credentials",
                                               ErrorCodes.INVALID_USERNAME_PASSWORD.code)

        is DataIntegrityViolationException -> ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message.orEmpty(),
                                                       ErrorCodes.DATA_INTEGRITY_VIOLATION.code)

        else -> {
            ApiError(HttpStatus.INTERNAL_SERVER_ERROR, this.message.orEmpty(), ErrorCodes.UNHANDLED.code)
        }
    }
    return apiError
}