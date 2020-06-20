package com.conectarSalud.home.affiliate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.consultation.consultationrequestinfo.ConsultationInfoActivity
import kotlinx.android.synthetic.main.activity_home_affiliate.*

class HomeAffiliateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_affiliate)

        btnStartCall.setOnClickListener {
            finish()
            val intent = Intent(this, ConsultationInfoActivity::class.java)
            startActivity(intent)
        }

    }

}