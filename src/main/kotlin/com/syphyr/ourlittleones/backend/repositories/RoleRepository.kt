package com.syphyr.ourlittleones.backend.repositories

import com.syphyr.ourlittleones.backend.database.tables.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Int>
{
    fun findByAuthority(authority: String): Optional<Role>
}