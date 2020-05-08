package com.conectarSalud.connector.backend

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import org.json.JSONObject


object BackendConnector {

    private const val BASE_PATH = "https://connecting-health.herokuapp.com"

    // TODO hacer que devuelva un generic
    fun post(path: String, bodyParams: JSONObject, completionHandler: (JSONObject?) -> Unit, errorHandler: (VolleyError?) -> Unit) {
        val request = JsonObjectRequest(Request.Method.POST, BASE_PATH + path, bodyParams,
            Response.Listener { completionHandler(it) }, Response.ErrorListener { errorHandler(it) })
        // Add request to handler
        RequestHandler.getInstance()
            .addToRequestQueue(request)
    }

    private fun url(path: String, params: MutableMap<String, String>): String =
        StringBuilder(BASE_PATH + path).apply {
            val queryString = paramString(params)
            if (queryString.isNotBlank()) append("?$queryString")
        }.toString()

    private fun paramString(params: MutableMap<String, String>): String =
        params.entries.joinToString("&") { "${it.key}=${it.value}" }


}