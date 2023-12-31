package com.syphyr.ourlittleones.backend.database.configuration

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import com.syphyr.ourlittleones.backend.database.tables.Role
import com.syphyr.ourlittleones.backend.repositories.RoleRepository
import com.syphyr.ourlittleones.backend.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DatabaseConfiguration(private val roleRepository: RoleRepository,
                            private val userRepository: UserRepository,
                            private val passwordEncoder: PasswordEncoder) {

    private fun dataAlreadySeeded() = roleRepository.findByAuthority("ADMIN").isPresent

    fun seed() {
        if (!dataAlreadySeeded()) {
            val roles = listOf(
                    Role(authority = "ADMIN"),
                    Role(authority = "GUEST_USER"),
                    Role(authority = "VERIFIED_USER"),
                    Role(authority = "BUYER"),
                    Role(authority = "SELLER")
            )
            roleRepository.saveAll(roles)

            val adminRole = roles.first { it.authority == "ADMIN" }
            val adminUser = ApplicationUser(
                    firstName = "Muhammad",
                    lastName = "Ahmed",
                    username = "admin",
                    password = passwordEncoder.encode("password"),
                    authorities = setOf(adminRole),
                    email = "m.ahmed.abutalib@gmail.com",
                    isEnabled = true)
            userRepository.save(adminUser)
        }
    }
}
