package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import com.syphyr.ourlittleones.backend.database.tables.RefreshToken
import com.syphyr.ourlittleones.backend.repositories.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
@Transactional
class RefreshTokenService(private val refreshTokenRepository: RefreshTokenRepository) {

    fun generateRefreshToken(user: ApplicationUser): RefreshToken {
        val expiry = Instant.now().plus(Duration.ofDays(10))
        deleteCurrentTokensForUser(user)
        val refreshToken = RefreshToken(user = user, token = UUID.randomUUID().toString(), expiryDate = expiry)
        return refreshTokenRepository.save(refreshToken)
    }

    private fun deleteCurrentTokensForUser(user: ApplicationUser) {
        refreshTokenRepository.findByUser_UserId(user.userId).map {
            refreshTokenRepository.delete(it)
            refreshTokenRepository.flush() // maybe a performance hit ?
        }
    }

    fun findByToken(token: String?): Optional<RefreshToken> {
        return refreshTokenRepository.findByToken(token!!)
    }

    fun deleteRefreshToken(token: RefreshToken): RefreshToken {
        refreshTokenRepository.delete(token)
        return token
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.expiryDate < Instant.now()) {
            refreshTokenRepository.delete(token)
            throw RuntimeException(token.token + " Refresh token is expired. Please re-login!")
        }
        return token
    }
}