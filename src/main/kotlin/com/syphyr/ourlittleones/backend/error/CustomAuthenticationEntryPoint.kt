package com.syphyr.ourlittleones.backend.error

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.OutputStream


@Component("customAuthenticationEntryPoint")
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authException: AuthenticationException?
    ) {

        val apiError = ApiError(HttpStatus.UNAUTHORIZED, authException?.message.orEmpty(),
                                ErrorCodes.SECURITY_EXCEPTION.code,
                                System.currentTimeMillis())
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseStream: OutputStream = response!!.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, apiError)
        responseStream.flush()
    }
}
