package com.syphyr.ourlittleones.backend.configurations

import java.security.KeyPair
import java.security.KeyPairGenerator

object KeyGeneratorUtility {

    fun generateRsaKey(): KeyPair {
        return try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPairGenerator.generateKeyPair()
        } catch (ex: Exception) {
            throw IllegalStateException()
        }
    }
}