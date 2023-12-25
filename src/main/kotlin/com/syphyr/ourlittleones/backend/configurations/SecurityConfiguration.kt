package com.syphyr.ourlittleones.backend.configurations

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.syphyr.ourlittleones.backend.error.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val keys: RsaKeyProperties) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
                .authorizeHttpRequests {
                    it.requestMatchers("/auth/**").permitAll()
                    it.requestMatchers("/user/**").hasAnyRole("ADMIN", "GUEST_USER")
                    it.anyRequest().authenticated()
                }.oauth2ResourceServer { oAuthResourceServer ->
                    oAuthResourceServer.jwt { oAuthResourceServerConfigurer ->
                        oAuthResourceServerConfigurer.jwtAuthenticationConverter(jwtAuthenticationConvertor())
                    }
                    oAuthResourceServer.authenticationEntryPoint(CustomAuthenticationEntryPoint())
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(keys.rsaPublicKey).privateKey(keys.rsaPrivateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return org.springframework.security.oauth2.jwt.NimbusJwtEncoder(jwks)
    }
    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keys.rsaPublicKey).build()
    }
    @Bean
    fun authenticationManager(userDetailsService: UserDetailsService): AuthenticationManager {
        val daoProvider = DaoAuthenticationProvider()
        daoProvider.setUserDetailsService(userDetailsService)
        daoProvider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(daoProvider)
    }

    @Bean
    fun jwtAuthenticationConvertor(): JwtAuthenticationConverter {
        val authoritiesConvertor = JwtGrantedAuthoritiesConverter()
        authoritiesConvertor.setAuthoritiesClaimName("roles")
        authoritiesConvertor.setAuthorityPrefix("ROLE_")

        val jwtConvertor = JwtAuthenticationConverter()

        jwtConvertor.setJwtGrantedAuthoritiesConverter(authoritiesConvertor)
        return jwtConvertor
    }

}