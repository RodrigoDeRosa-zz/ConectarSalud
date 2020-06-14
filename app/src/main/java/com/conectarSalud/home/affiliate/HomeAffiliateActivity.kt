package com.conectarSalud.home.affiliate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.videoChat.VideoChatViewActivity
import com.conectarSalud.videoChat.WaitingRoomActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_affiliate.*

class HomeAffiliateActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_affiliate)

        btnStartCall.setOnClickListener {
            finish()
            val intent = Intent(this, WaitingRoomActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    val intent = Intent(this, HomeAffiliateActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.page_history -> {
                    finish()
                    val intent = Intent(this, HomeAffiliateActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

}