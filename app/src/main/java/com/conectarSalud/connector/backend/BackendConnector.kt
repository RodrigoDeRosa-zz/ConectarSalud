package com.conectarSalud.connector.backend

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.conectarSalud.model.loginuser.LoginResult
import org.json.JSONArray
import org.json.JSONObject


object BackendConnector {

    private const val BASE_PATH = "http://10.0.2.2:8080/api"

    // TODO hacer que devuelva un generic
    fun post(
        path: String,
        bodyParams: JSONObject,
        completionHandler: (JSONArray?) -> LoginResult,
        errorHandler: () -> LoginResult
    ) {
        val request = JsonArrayRequest(
            Request.Method.POST, path, JSONArray().put(bodyParams),
            Response.Listener { completionHandler(it) },
            Response.ErrorListener { errorHandler() }
        )
        // Add request to handler
        RequestHandler.getInstance()
            .addToRequestQueue(request)
    }

    private fun url(path: String, params: MutableMap<String, String>): String =
        StringBuilder(BASE_PATH + path).apply {
            val queryString =
                paramString(
                    params
                )
            if (queryString.isNotBlank()) append("?$queryString")
        }.toString()

    private fun paramString(params: MutableMap<String, String>): String =
        params.entries.joinToString("&") { "${it.key}=${it.value}" }


}