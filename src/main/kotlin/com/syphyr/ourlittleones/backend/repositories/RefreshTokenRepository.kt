package com.syphyr.ourlittleones.backend.repositories

import com.syphyr.ourlittleones.backend.database.tables.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Int> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun findByUser_UserId(userId: Int): Optional<RefreshToken>
}