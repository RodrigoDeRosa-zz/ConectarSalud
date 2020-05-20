package com.conectarSalud.connector.backend

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


object BackendConnector {

    private const val BASE_PATH = "https://connecting-health.herokuapp.com"

    fun get(path: String, completionHandler: (JSONObject?) -> Unit, errorHandler: (VolleyError?) -> Unit) {
        val request = JsonObjectRequest(Request.Method.GET, BASE_PATH + path, JSONObject(),
            Response.Listener { completionHandler(it) }, Response.ErrorListener { errorHandler(it) })
        // Add request to handle
        RequestHandler.getInstance()
            .addToRequestQueue(request)
    }

    fun post(path: String, bodyParams: JSONObject?, completionHandler: (JSONObject?) -> Unit, errorHandler: (VolleyError?) -> Unit) {
        val request = JsonObjectRequest(Request.Method.POST, BASE_PATH + path,
            bodyParams ?: JSONObject(),
            Response.Listener { completionHandler(it) }, Response.ErrorListener { errorHandler(it) })
        // Add request to handle
        RequestHandler.getInstance()
            .addToRequestQueue(request)
    }

    fun patch(path: String, bodyParams: JSONObject, completionHandler: (JSONObject?) -> Unit, errorHandler: (VolleyError?) -> Unit) {
        val request = CustomJsonObjectRequest(Request.Method.PATCH, BASE_PATH + path, bodyParams,
            Response.Listener { completionHandler(it) }, Response.ErrorListener { errorHandler(it) })
        // Add request to handle
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