package com.conectarSalud.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.conectarSalud.R
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.consultation.consultationrequestinfo.ConsultationInfoActivity
import com.conectarSalud.consultation.watingconsultation.WaitingRoomActivity
import com.conectarSalud.model.consultation.activeConsultationDTO
import com.conectarSalud.model.consultation.familyMemberDTO
import com.conectarSalud.services.Resources


class AffiliateHomeFragment: Fragment() {

    private lateinit var family : List<String>
    private lateinit var listView: ListView

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

        this.consultationConnector.getFamilyData(::setFamilyAfterRequest)

        val btnStartCall: Button = view.findViewById(R.id.btnStartCall) as Button
        btnStartCall.setOnClickListener {
            //call back if there are existing consultations
//            Log.d("AAAAAAAAAA", "checked: " + family[listView.checkedItemPosition])
            val patientDNI :String = family[listView.checkedItemPosition].split("-")[0].split(" ")[0]
            Resources.patientDni = patientDNI
            this.consultationConnector.getActiveConsultations(::setActiveConsultationAfterRequest)
        }

        return view
    }

    private fun setFamilyAfterRequest(result: ArrayList<familyMemberDTO>?) {

        family = result?.sortedBy { it.dni != Resources.dni }?.map { it.dni + " - " + it.affiliate_first_name + " " + it.affiliate_last_name }!!

        //Family list
        listView = this.view?.findViewById(R.id.listView)!!
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        val adapter: Adapter = ArrayAdapter(this.view?.context!!, android.R.layout.simple_list_item_single_choice, family)
        listView.adapter = adapter as ListAdapter?
        //set the first as default
        listView.setItemChecked(0, true)
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