package com.conectarSalud

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.connector.backend.RequestHandler
import com.conectarSalud.home.HomeActivity
import com.conectarSalud.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up application wide request handler
        RequestHandler.configure(context = this)

        // Redirects to home if user is logged in or login if not
        if (this.userIsLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun userIsLoggedIn():Boolean {
        //TODO local storage
        return false
    }

}