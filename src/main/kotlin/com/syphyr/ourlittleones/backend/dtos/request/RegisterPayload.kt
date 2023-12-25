package com.syphyr.ourlittleones.backend.dtos.request

data class RegisterPayload(val username: String, val password: String, val email: String, val role: String)