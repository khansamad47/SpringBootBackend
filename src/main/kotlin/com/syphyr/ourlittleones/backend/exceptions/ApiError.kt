package com.syphyr.ourlittleones.backend.exceptions

import org.springframework.http.HttpStatus

data class ApiError(val status: HttpStatus,
                    val message: String,
                    val errorCode: Int,
                    val timeStamp: Long = System.currentTimeMillis())