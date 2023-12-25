package com.syphyr.ourlittleones.backend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException

fun Exception.toApiError(): ApiError {
    return when (this) {
        is BadCredentialsException -> ApiError(HttpStatus.UNAUTHORIZED, "Incorrect Credentials", 2)
        else -> {
            ApiError(HttpStatus.INTERNAL_SERVER_ERROR, this.message.orEmpty(), 3)
        }
    }
}