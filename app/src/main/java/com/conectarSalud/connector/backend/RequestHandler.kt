package com.conectarSalud.connector.backend

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class RequestHandler constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: RequestHandler? = null
        // Instance creator
        fun configure(context: Context) {
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: RequestHandler(
                        context
                    )
                        .also { INSTANCE = it }
            }
        }
        // Instance accessor. Not a real singleton but a way to avoid passing AppContext around.
        // This is dangerous, it will explode if configure is not called on main activity's onCreate
        fun getInstance(): RequestHandler = INSTANCE!!
    }

    // Request queue creator
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>): Request<T> = requestQueue.add(request)

}