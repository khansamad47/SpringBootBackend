package com.syphyr.ourlittleones.backend.database.tables

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int = 0,
                        val token: String,
                        @Column(name = "expiry_date")
                        val expiryDate: Instant,
                        @OneToOne
                        @JoinColumn(name = "user_id", referencedColumnName = "user_id")
                        val user: ApplicationUser)