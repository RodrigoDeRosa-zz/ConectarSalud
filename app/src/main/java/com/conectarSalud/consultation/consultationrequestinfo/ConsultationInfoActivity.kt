package com.conectarSalud.consultation.consultationrequestinfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.connector.consultation.consultationinfo.ConsultationInfoConnector
import com.conectarSalud.consultation.watingconsultation.WaitingRoomActivity
import com.conectarSalud.util.CustomExpandableListAdapter
import kotlinx.android.synthetic.main.activity_consultation_info.*
import org.json.JSONObject

class ConsultationInfoActivity : AppCompatActivity() {
    private val consultationInfoActivity: ConsultationInfoConnector = ConsultationInfoConnector()
    private val selectedSymptoms = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_info)

        consultationInfoActivity.getSymptoms(::addTextOptionWithList)

        btnGoWaitingRoom.setOnClickListener {
            if (selectedSymptoms.size > 0) {
                val parameters = JSONObject()
                parameters.put("symptoms", selectedSymptoms.toString())
                parameters.put("reason", editTextTextConsultationReason.text.toString())

                finish()
                val intent = Intent(this, WaitingRoomActivity::class.java)
                intent.putExtra("bodyParams", parameters.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Debe seleccionar al menos un sintoma", Toast.LENGTH_SHORT).show()
            }
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
    }

    @SuppressLint("ResourceAsColor")
    private fun handleChildClick(expandableListAdapter: CustomExpandableListAdapter, childView: View, groupPosition: Int, childPosition: Int): Boolean {
        val symptom: String = expandableListAdapter.getChild(groupPosition, childPosition) as String
        if (symptom in selectedSymptoms) {
            //childView.findViewById<TextView>(R.id.childListName).setTextColor(R.color.grey)
            selectedSymptoms.remove(symptom)
        } else {
            //childView.findViewById<TextView>(R.id.childListName).setTextColor(R.color.white)
            //childView.findViewById<TextView>(R.id.childListName).setTextColor(R.color.secondaryGreen)
            selectedSymptoms.add(symptom)
        }
        return false
    }

}