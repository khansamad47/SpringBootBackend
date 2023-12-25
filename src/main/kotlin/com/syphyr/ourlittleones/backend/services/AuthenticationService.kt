package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import com.syphyr.ourlittleones.backend.dtos.response.RegisterResponse
import com.syphyr.ourlittleones.backend.dtos.response.TokenResponse
import com.syphyr.ourlittleones.backend.error.ApiError
import com.syphyr.ourlittleones.backend.error.toApiError
import com.syphyr.ourlittleones.backend.functional.Either
import com.syphyr.ourlittleones.backend.repositories.RoleRepository
import com.syphyr.ourlittleones.backend.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationService(private val userRepository: UserRepository,
                            private val roleRepository: RoleRepository,
                            private val tokenService: TokenService,
                            private val refreshTokenService: RefreshTokenService,
                            private val authenticationManager: AuthenticationManager,
                            private val passwordEncoder: PasswordEncoder) {


    fun registerUser(username: String,
                     password: String,
                     email: String,
                     role: String): RegisterResponse {
        val encodedPassword = passwordEncoder.encode(password)
        val userRole = roleRepository.findByAuthority(role).get()
        val addedUser = userRepository.save(
                ApplicationUser(username = username,
                                password = encodedPassword,
                                email = email,
                                authorities = setOf(userRole),
                                isEnabled = true))

        return RegisterResponse(username = addedUser.username, email = addedUser.email)

    }


    fun login(username: String, password: String): TokenResponse {
        val user = userRepository.findUserByUsername(username).get()
        val token = tokenService.generateJwt(user)
        val refreshToken = refreshTokenService.generateRefreshToken(user)
        return TokenResponse(token = token, refreshToken = refreshToken.token)
    }
}