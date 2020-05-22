package com.conectarSalud.model.loginuser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val responseCode: Int? = null,
        val error: Int? = null
)
