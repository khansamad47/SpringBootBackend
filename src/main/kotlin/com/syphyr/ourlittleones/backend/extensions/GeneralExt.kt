package com.syphyr.ourlittleones.backend.extensions

import org.springframework.http.ResponseEntity

fun <T> T.toResponseEntity(): ResponseEntity<T> = ResponseEntity.ok(this)