package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.dtos.response.UserResponse
import com.syphyr.ourlittleones.backend.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserByUsername(username)
                .orElseThrow { UsernameNotFoundException("user is not valid") }
    }

    fun getAuthenticatedUser(username: String): UserResponse {
        val user = userRepository.findUserByUsername(username).orElseThrow {
            UsernameNotFoundException("user is not valid")
        }
        return UserResponse(userId = user.userId,
                            username = user.username,
                            email = user.email,
                            accessToken = user.accessToken,
                            refreshToken = user.refreshToken,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            roles = user.authorities.joinToString { it.authority })
    }
}