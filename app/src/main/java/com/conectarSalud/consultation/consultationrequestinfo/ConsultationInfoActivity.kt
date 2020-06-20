package com.conectarSalud.consultation.consultationrequestinfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.connector.consultation.consultationinfo.ConsultationInfoConnector
import com.conectarSalud.consultation.watingconsultation.WaitingRoomActivity
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.util.CustomExpandableListAdapter
import kotlinx.android.synthetic.main.activity_consultation_info.*
import kotlinx.android.synthetic.main.activity_home_medic.*
import org.json.JSONObject

class ConsultationInfoActivity : AppCompatActivity() {
    private val consultationInfoActivity: ConsultationInfoConnector = ConsultationInfoConnector()
    private val selectedSymptoms = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_info)

        consultationInfoActivity.getSymptoms(::addTextOptionWithList)

        btnGoWaitingRoom.setOnClickListener {
            val parameters = JSONObject()
            parameters.put("symptoms", selectedSymptoms.toString())
            parameters.put("reason", editTextTextConsultationReason.text.toString())

            // TODO check parameters
            finish()
            val intent = Intent(this, WaitingRoomActivity::class.java)
            intent.putExtra("bodyParams", parameters.toString())
            startActivity(intent)
        }
    }

    private fun addTextOptionWithList(data: HashMap<String, List<String>>) {

        // Set custom adapter for expandable list
        val expandableListAdapter = CustomExpandableListAdapter(
            this,
            ArrayList(data.keys),
            data
        )
        symptomsList.setAdapter(expandableListAdapter)
        symptomsList.setOnGroupExpandListener { }
        symptomsList.setOnGroupCollapseListener { }
        symptomsList.setOnChildClickListener { _, childView, groupPosition, childPosition, _ ->
            handleChildClick(expandableListAdapter, childView, groupPosition, childPosition)
        }
        //list.forEach(::transformToView)
    }

    private fun handleChildClick(expandableListAdapter: CustomExpandableListAdapter, childView: View, groupPosition: Int, childPosition: Int): Boolean {
        val symptom: String = expandableListAdapter.getChild(groupPosition, childPosition) as String
        if (symptom in selectedSymptoms) {
            //childView.findViewById<TextView>(R.id.childListName).setTextColor(R.color.grey)
            selectedSymptoms.remove(symptom)
        } else {
            //childView.findViewById<TextView>(R.id.childListName).setTextColor(R.color.secondaryGreen)
            selectedSymptoms.add(symptom)
        }
        return false
    }

}
