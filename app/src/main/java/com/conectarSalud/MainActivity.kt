package com.conectarSalud

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.videoChat.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*btnStartCall.setOnClickListener {
            val intent = Intent(this, VideoChatViewActivity::class.java)
            startActivity(intent)
        }*/

    }

}