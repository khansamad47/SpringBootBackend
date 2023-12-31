package com.syphyr.ourlittleones.backend.controllers

import com.syphyr.ourlittleones.backend.dtos.request.RegisterPayload
import com.syphyr.ourlittleones.backend.extensions.toResponseEntity
import com.syphyr.ourlittleones.backend.services.AuthenticationService
import com.syphyr.ourlittleones.backend.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
class UserController(private val userService: UserService) {


    @GetMapping("/")
    fun getUser(): ResponseEntity<*> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return ResponseEntity.ok(userService.getAuthenticatedUser(authentication.name))
    }


}