package com.conectarSalud.connector.login

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.R
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.model.loginuser.LoggedInUser
import com.conectarSalud.model.loginuser.LoginResult
import org.json.JSONObject

class LoginConnector() {

    private val LOGIN_PATH = "/authenticate"
    private val BAD_CREDENTIALS = 404
    private val mapper = Klaxon()
    private lateinit var callback: (result:LoginResult) -> Unit

    fun login(username: String, password: String, callback: (result:LoginResult) -> Unit) {
        // save callback
        this.callback = callback
        // do login
        val parameters = JSONObject()
        parameters.put("user_id", username)
        parameters.put("password", password)
        BackendConnector.post(LOGIN_PATH, parameters, ::correctResponseHandler, ::errorResponseHandler)
    }

    private fun correctResponseHandler(response :JSONObject?) {
        val userData: LoggedInUser? = mapper.parse<LoggedInUser>(response.toString())
        callback(LoginResult(responseCode = 200, userData = userData))
    }

    private fun errorResponseHandler(error: VolleyError?) {
        val errorMessage = if (error?.networkResponse?.statusCode == BAD_CREDENTIALS)
                            R.string.login_failed else R.string.unknown_login_error
        callback(LoginResult(error = errorMessage))
    }
}

