package com.syphyr.ourlittleones.backend.dtos.response

data class UserResponse(val userId: Int,
                        val username: String,
                        val firstName: String,
                        val lastName: String,
                        val email: String,
                        val accessToken: String,
                        val refreshToken: String,
                        val roles: String)