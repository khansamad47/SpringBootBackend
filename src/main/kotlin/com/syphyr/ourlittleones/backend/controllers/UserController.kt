package com.syphyr.ourlittleones.backend.controllers

import com.syphyr.ourlittleones.backend.dtos.request.LoginPayload
import com.syphyr.ourlittleones.backend.dtos.request.RegisterPayload
import com.syphyr.ourlittleones.backend.error.toApiError
import com.syphyr.ourlittleones.backend.extensions.toResponseEntity
import com.syphyr.ourlittleones.backend.functional.toResponseEntity
import com.syphyr.ourlittleones.backend.services.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
class UserController(private val authenticationService: AuthenticationService) {


    @GetMapping("/")
    fun getUser(@RequestBody registerPayload: RegisterPayload): ResponseEntity<*> {
        return "han bhaii".toResponseEntity()
    }

//    @PostMapping("/login")
//    fun login(@RequestBody loginPayload: LoginPayload): ResponseEntity<*> {
//        return authenticationService
//                .registerUser(username = registerPayload.username,
//                              password = registerPayload.password,
//                              email = registerPayload.email,
//                              role = registerPayload.role)
//                .toResponseEntity()
//    }

//    @PostMapping("/login")
//    fun login(@RequestBody registerDto: RegisterDto): ResponseEntity<*> {
//
//         return authenticationService
//                 .login(username = registerDto.username, password = registerDto.password)
//                 .toResponseEntity()
//    }
//
//    @PostMapping("/refreshToken")
//    fun refreshToken(@RequestBody refreshTokenDto: RefreshTokenDto): JwtResponse {
//        val respponse = authenticationService.refreshToken(refreshTokenDto.refreshToken)
//        return respponse
//    }


}