package com.syphyr.ourlittleones.backend.dtos.response

data class UserResponse(val userId: Int,
                        val username: String,
                        val email: String,
                        val accessToken: String,
                        val refreshToken: String,
                        val roles: String)