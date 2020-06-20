package com.conectarSalud.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.conectarSalud.R
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.consultation.consultationrequestinfo.ConsultationInfoActivity
import com.conectarSalud.consultation.watingconsultation.WaitingRoomActivity
import com.conectarSalud.model.consultation.activeConsultationDTO
import com.conectarSalud.model.consultation.consultationDTO
import com.conectarSalud.services.Resources
import kotlinx.android.synthetic.main.activity_consultation_info.*
import org.json.JSONObject


class AffiliateHomeFragment: Fragment() {

    private var consultationConnector = ConsultationConnector()

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
            //call back if there are existing consultations
            this.consultationConnector.getActiveConsultations(::setActiveConsultationAfterRequest)
        }

        return view
    }

    private fun setActiveConsultationAfterRequest(result: activeConsultationDTO?) {

        if (result != null && result.symptoms.isNotEmpty()){

            Resources.symptoms = result
            val intent = Intent(context, WaitingRoomActivity::class.java)

            startActivity(intent)
        } else {
            val intent = Intent(context, ConsultationInfoActivity::class.java)
            startActivity(intent)
        }

    }

}