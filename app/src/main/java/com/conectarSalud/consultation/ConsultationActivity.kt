package com.conectarSalud.consultation;
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.conectarSalud.R
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.model.consultation.consultationDTO

class ConsultationActivity : AppCompatActivity() {

    private var consultationID = ""
    private var ratingConnector = ConsultationConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation)

        val b = intent.extras
        if (b != null) consultationID = b.getString("consultationID").toString()

        this.ratingConnector.getConsultationData(consultationID, ::loadActivityAfterRequest)
    }

    private fun loadActivityAfterRequest(result: consultationDTO?) {

        if (result != null) {
            val date = findViewById<TextView>(R.id.consultationDateContent)
            date.text = Html.fromHtml(result.date)
            val affiliate = findViewById<TextView>(R.id.consultationAffiliateContent)
            affiliate.text = Html.fromHtml(result.patientFirstName + " " + result.patientLastName)
            val doctor = findViewById<TextView>(R.id.consultationDoctorContent)
            doctor.text = Html.fromHtml(result.doctorFirstName + " " + result.doctorLastName)
            val specialties = findViewById<TextView>(R.id.consultationSpecialitiesContent)
            var specialtiesText = ""
            result.doctorSpecialities.forEachIndexed() { index, specialty ->
                run {
                    specialtiesText += specialty
                    if (index == result.doctorSpecialities.size - 1) {
                        specialtiesText += "\n"
                    }
                }
            }
            specialties.text = Html.fromHtml(specialtiesText)
            val symptoms = findViewById<TextView>(R.id.consultationSymptomsContent)
            var symptomsText = ""
            result.symptoms.forEachIndexed() { index, symptom ->
                run {
                    symptomsText += symptom
                    if (index == result.symptoms.size - 1) {
                        symptomsText += "\n"
                    }
                }
            }
            symptoms.text = Html.fromHtml(symptomsText)

            //indications
            val indications = findViewById<TextView>(R.id.consultationIndicationsContent)
            if (result.indications != null){
                indications.text = Html.fromHtml(result.indications)
            } else {
                val indicationsTitle = findViewById<TextView>(R.id.consultationIndicationsTitle)
                indications.visibility = View.GONE
                indicationsTitle.visibility = View.GONE
            }


            //prescription
            if(result.hasPrescription){

                val loadPrescription = findViewById<Button>(R.id.loadPrescription)
                loadPrescription.visibility = View.VISIBLE
                loadPrescription.setOnClickListener {
                    startActivity(Intent(this, PrescriptionActivity::class.java))
                }

            }

        } else {
            Toast.makeText(this, "There was an error recovering the consultation information",
                Toast.LENGTH_SHORT).show()
        }

        val loader = findViewById<ProgressBar>(R.id.loadearRating)
        loader.visibility = View.GONE

    }

}
