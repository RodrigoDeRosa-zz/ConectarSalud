package com.conectarSalud.home.affiliate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.videoChat.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_home_affiliate.*

class HomeAffiliateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_affiliate)

        btnStartCall.setOnClickListener {
            val intent = Intent(this, VideoChatViewActivity::class.java)
            startActivity(intent)
        }

    }

}