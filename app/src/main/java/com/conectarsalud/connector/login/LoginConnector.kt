package com.conectarsalud.connector.login

import com.conectarsalud.R
import com.conectarsalud.connector.backend.BackendConnector
import com.conectarsalud.model.loginuser.LoginResult
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject

class LoginConnector() {

    private val LOGIN_PATH = "/login"
    private val mapper = jacksonObjectMapper()

    fun login(username: String, password: String) {
        // do login
        val parameters = JSONObject()
        parameters.put("user_id", username)
        parameters.put("password", password)
        BackendConnector.post(LOGIN_PATH, parameters, ::createCompletionResponse, ::createErrorRespose)
    }

    private fun createCompletionResponse(response: JSONArray?): LoginResult {
        // TODO ver como handlea la respuesta y agregar la data del usuario
        return LoginResult(responseCode = 200)
    }

    private fun createErrorRespose(): LoginResult {
        // TODO pasarle el nro de error y crear las excepciones correspondientes
        return LoginResult(error = R.string.login_failed)
    }
}
