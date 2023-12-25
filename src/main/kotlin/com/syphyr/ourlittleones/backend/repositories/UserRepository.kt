package com.syphyr.ourlittleones.backend.repositories

import com.syphyr.ourlittleones.backend.database.tables.ApplicationUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<ApplicationUser, Int>