package com.conectarSalud.rating

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.model.consultation.consultationDTO
import com.google.android.material.textfield.TextInputEditText

class RatingActivity : AppCompatActivity() {

    private var consultationID = ""
    private var doctorName = ""
    private var ratingConnector = ConsultationConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        val b = intent.extras
        if (b != null) consultationID = b.getString("consultationID").toString()

        setRatingTitle()

        val loadRating = findViewById<Button>(R.id.loadRating)
        loadRating.isEnabled = false
        val ratingBar = findViewById<RatingBar>(R.id.rating)
        val ratingComment = findViewById<TextInputEditText>(R.id.ratingComment)

        ratingBar.setOnRatingBarChangeListener { _, _, _ ->  run { loadRating.isEnabled = true }}

        loadRating.setOnClickListener {
            ratingConnector.patchScore(consultationID, ratingBar.rating,
                ratingComment.text.toString(), ::handleRatingAfterRequest)
        }
    }

    private fun setRatingTitle() {
        this.ratingConnector.getConsultationData(consultationID, ::setDoctorAfterRequest)
    }

    private fun setDoctorAfterRequest(result: consultationDTO?) {

        val text:String

        if (result != null) {
            doctorName = result.doctorFirstName + " " + result.doctorLastName
            text = "Calificá tu experiencia con el <br><strong>Dr. $doctorName<strong>"
        } else {
            text = "Calificá tu experiencia"
            Toast.makeText(this, "There was an error recovering the doctor information",
                Toast.LENGTH_SHORT).show()
        }

        val ratingTitle = findViewById<TextView>(R.id.ratingTitle)
        ratingTitle.text = Html.fromHtml(text)
        val loader = findViewById<ProgressBar>(R.id.loadearRating)
        loader.visibility = View.GONE

    }

    private fun handleRatingAfterRequest(requestOK: Boolean?) {
        if(!requestOK!!){
            Toast.makeText(this, "There was an error setting your rating",
                Toast.LENGTH_SHORT).show()
        }
        finish()
        startActivity(Intent(this, HomeAffiliateActivity::class.java))
    }

}