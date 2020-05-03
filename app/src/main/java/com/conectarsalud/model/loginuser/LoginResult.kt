package com.conectarsalud.model.loginuser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val responseCode: Int? = null,
        val userData: LoggedInUser? = null,
        val error: Int? = null
)
