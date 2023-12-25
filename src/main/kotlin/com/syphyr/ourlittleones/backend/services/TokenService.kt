package com.syphyr.ourlittleones.backend.services

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class TokenService(private val jwtEncoder: JwtEncoder) {

    fun generateJwt(user: ApplicationUser): String {

        val now = Instant.now()
        val expiry = Instant.now().plus(Duration.ofDays(1))
        val scope = user.authorities.joinToString(separator = " ") { it.authority }

        val claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(user.username)
                .claims { claimsMap -> claimsMap["roles"] = scope }
                .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
    //use one or the other here . Delete the left over
}