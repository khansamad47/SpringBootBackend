package com.syphyr.ourlittleones.backend.dtos.request

data class RegisterPayload(val firstName: String,
                           val lastName: String,
                           val username: String,
                           val password: String,
                           val email: String,
                           val role: String)