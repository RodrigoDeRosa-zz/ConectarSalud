package com.conectarSalud

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.connector.backend.RequestHandler
import com.conectarSalud.helper.SocketHandler
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.login.LoginActivity
import com.conectarSalud.rating.RatingActivity
import com.conectarSalud.services.Resources

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up application wide request handler
        RequestHandler.configure(context = this)

        // TODO do it when the connection is required
        SocketHandler.connectSocket()

        // Redirects to home if user is logged in or login if not
        if (this.userIsLoggedIn()) {
            startActivity(Intent(this, HomeAffiliateActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun userIsLoggedIn():Boolean {
        return Resources.userLogged
    }

}