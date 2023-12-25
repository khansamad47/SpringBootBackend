package com.syphyr.ourlittleones.backend.configurations

import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
//This can be turned into a kotlin object too.
class RsaKeyProperties() {
    private val pair = KeyGeneratorUtility.generateRsaKey()

    final var rsaPublicKey: RSAPublicKey = pair.public as RSAPublicKey
        private set

    final var rsaPrivateKey: RSAPrivateKey = pair.private as RSAPrivateKey
        private set
}
