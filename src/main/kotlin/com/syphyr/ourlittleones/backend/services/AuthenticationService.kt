package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import com.syphyr.ourlittleones.backend.database.tables.RefreshToken
import com.syphyr.ourlittleones.backend.dtos.response.RegisterResponse
import com.syphyr.ourlittleones.backend.dtos.response.TokenResponse
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


    fun registerUser(
            firstName: String,
            lastName: String,
            username: String,
            password: String,
            email: String,
            role: String): RegisterResponse {
        val encodedPassword = passwordEncoder.encode(password)
        val userRole = roleRepository.findByAuthority(role).get()
        val addedUser = userRepository.save(
                ApplicationUser(firstName = firstName,
                                lastName = lastName,
                                username = username,
                                password = encodedPassword,
                                email = email,
                                accessToken = "",
                                refreshToken = "",
                                photoUrl = "https://picsum.photos/500/500",
                                authorities = setOf(userRole),
                                isEnabled = true))

        return RegisterResponse(userId = addedUser.userId, username = addedUser.username, email = addedUser.email)

    }


    fun authenticate(username: String, password: String): TokenResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val user = userRepository.findUserByUsername(username).get()
        val token = tokenService.generateJwt(user)
        val refreshToken = refreshTokenService.generateRefreshToken(user)
        userRepository.save(user.copy(accessToken = token, refreshToken = refreshToken.token))
        return TokenResponse(token = token, refreshToken = refreshToken.token)
    }

    fun refreshToken(refreshToken: String): TokenResponse {
        return refreshTokenService.findByToken(refreshToken)
                .map { refreshTokenService.verifyExpiration(it) }
                .map(RefreshToken::user)
                .map { user ->
                    val newToken = tokenService.generateJwt(user)
                    val newRefreshToken = refreshTokenService.generateRefreshToken(user)
                    TokenResponse(token = newToken, refreshToken = newRefreshToken.token)
                }.orElseThrow()
    }
}