package com.conectarSalud.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.conectarSalud.R
import com.conectarSalud.consultation.consultationrequestinfo.ConsultationInfoActivity


class AffiliateHomeFragment: Fragment() {

    companion object {
        fun newInstance(): AffiliateHomeFragment = AffiliateHomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_home_affiliate,
            container, false
        )

        val btnStartCall: Button = view.findViewById(R.id.btnStartCall) as Button
        btnStartCall.setOnClickListener {
            val intent = Intent(context, ConsultationInfoActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}