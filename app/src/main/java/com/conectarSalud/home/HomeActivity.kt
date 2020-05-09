package com.conectarSalud.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.videoChat.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnStartCall.setOnClickListener {
            val intent = Intent(this, VideoChatViewActivity::class.java)
            startActivity(intent)
        }

    }

}