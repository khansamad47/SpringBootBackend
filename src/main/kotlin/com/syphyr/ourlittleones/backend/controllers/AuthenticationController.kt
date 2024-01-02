package com.syphyr.ourlittleones.backend.controllers

import com.syphyr.ourlittleones.backend.dtos.request.AuthenticatePayload
import com.syphyr.ourlittleones.backend.dtos.request.RefreshTokenPayload
import com.syphyr.ourlittleones.backend.dtos.request.RegisterPayload
import com.syphyr.ourlittleones.backend.extensions.toResponseEntity
import com.syphyr.ourlittleones.backend.services.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
class AuthenticationController(private val authenticationService: AuthenticationService) {


    @PostMapping("/register")
    fun registerUser(@RequestBody registerPayload: RegisterPayload): ResponseEntity<*> {
        return authenticationService
                .registerUser(
                        firstName = registerPayload.firstName,
                        lastName = registerPayload.lastName,
                        username = registerPayload.username,
                        password = registerPayload.password,
                        email = registerPayload.email,
                        role = registerPayload.role)
                .toResponseEntity()

    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticatePayload: AuthenticatePayload): ResponseEntity<*> {
        return authenticationService
                .authenticate(
                        username = authenticatePayload.username,
                        password = authenticatePayload.password,
                )
                .toResponseEntity()
    }

    @PostMapping("/refreshToken")
    fun refreshToken(@RequestBody refreshTokenPayload: RefreshTokenPayload): ResponseEntity<*> {
        return authenticationService
                .refreshToken(refreshTokenPayload.refreshToken)
                .toResponseEntity()
    }


}