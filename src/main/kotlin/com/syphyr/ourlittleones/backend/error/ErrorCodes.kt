package com.syphyr.ourlittleones.backend.error

enum class ErrorCodes(val code: Int) {
    INVALID_USERNAME_PASSWORD(1),
    DATA_INTEGRITY_VIOLATION(2),
    UNHANDLED(10000)
}