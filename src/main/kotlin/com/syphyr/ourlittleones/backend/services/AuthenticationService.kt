package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import com.syphyr.ourlittleones.backend.dtos.response.RegisterResponse
import com.syphyr.ourlittleones.backend.exceptions.ApiError
import com.syphyr.ourlittleones.backend.exceptions.toApiError
import com.syphyr.ourlittleones.backend.functional.Either
import com.syphyr.ourlittleones.backend.repositories.RoleRepository
import com.syphyr.ourlittleones.backend.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationService(private val userRepository: UserRepository,
                            private val roleRepository: RoleRepository,
                            private val passwordEncoder: PasswordEncoder) {

    fun registerUser(username: String,
                     password: String,
                     email: String,
                     role: String): Either<ApiError, RegisterResponse> {
        return try {
            val encodedPassword = passwordEncoder.encode(password)
            val userRole = roleRepository.findByAuthority(role).get()
            val addedUser = userRepository.save(
                    ApplicationUser(username = username,
                                    password = encodedPassword,
                                    email = email,
                                    authorities = setOf(userRole),
                                    isEnabled = true))

            Either.Right(RegisterResponse(username = addedUser.username, email = addedUser.email))
        } catch (ex: Exception) {
            Either.Left(ex.toApiError())
        }
    }


    fun login(username: String, password: String) {
        TODO()
    }
}