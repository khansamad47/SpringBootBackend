package com.syphyr.ourlittleones.backend.database.tables

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class ApplicationUser(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        val userId: Int = 0,
        @Column(unique = true)
        private val username: String,
        private val firstName: String,
        private val lastName: String,
        private val password: String,
        @Column(columnDefinition = "TEXT")
        val accessToken: String,
        val refreshToken: String,
        val email: String,
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_role_junction",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        private val authorities: Set<Role> = emptySet(),
        @Column(name = "is_enabled")
        private val isEnabled: Boolean,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities.toMutableList()
    }

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.isEnabled
    }
}