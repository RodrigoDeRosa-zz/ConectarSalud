package com.conectarSalud.consultation

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.model.consultation.prescriptionDTO

class PrescriptionActivity : AppCompatActivity() {

    private var consultationID = ""
    private var ratingConnector = ConsultationConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        val b = intent.extras
        if (b != null) consultationID = b.getString("consultationID").toString()

        this.ratingConnector.getPrescriptionData(consultationID, ::loadActivityAfterRequest)
    }

    private fun loadActivityAfterRequest(result: prescriptionDTO?) {

        if (result != null) {
            val date = findViewById<TextView>(R.id.prescriptionDateContent)
            date.text = Html.fromHtml(result.date)
            val affiliate = findViewById<TextView>(R.id.prescriptionPatientNameContent)
            affiliate.text = Html.fromHtml(result.patientFirstName + " " + result.patientLastName)
            val doctor = findViewById<TextView>(R.id.prescriptionDoctorNameContent)
            doctor.text = Html.fromHtml(result.doctorFirstName + " " + result.doctorLastName)
            val specialties = findViewById<TextView>(R.id.prescriptionSpecialitiesContent)
            var specialtiesText = ""
            result.doctorSpecialties.forEachIndexed() { index, specialty ->
                run {
                    specialtiesText += specialty
                    if (index != result.doctorSpecialties.size - 1) {
                        specialtiesText += "; "
                    }
                }
            }
            specialties.text = Html.fromHtml(specialtiesText)

            val licence = findViewById<TextView>(R.id.prescriptionDoctorLicenceContent)
            licence.text = Html.fromHtml(result.doctorLicence)
            val plan = findViewById<TextView>(R.id.prescriptionPatientPlanContent)
            plan.text = Html.fromHtml(result.patientPlan)
            val affiliateNumber = findViewById<TextView>(R.id.prescriptionPatientNumberContent)
            affiliateNumber.text = Html.fromHtml(result.patientId)

            //prescriptionText
            val prescriptionText = findViewById<TextView>(R.id.consultationIndicationsContent)
            if (result.prescriptionText != null){
                prescriptionText.text = Html.fromHtml(result.prescriptionText)
            } else {
                prescriptionText.visibility = View.GONE
            }


        } else {
            Toast.makeText(this, "There was an error recovering the prescription information",
                Toast.LENGTH_SHORT).show()
        }

        val loader = findViewById<ProgressBar>(R.id.loaderPrescription)
        loader.visibility = View.GONE

    }

}