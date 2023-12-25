package com.syphyr.ourlittleones.backend.database.tables

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "roles")
class Role(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "role_id") val id: Int = 0,
           private val authority: String) :
        GrantedAuthority {
    override fun getAuthority() = authority
}