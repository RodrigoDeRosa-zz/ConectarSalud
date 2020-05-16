package com.conectarSalud.rating

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.google.android.material.textfield.TextInputEditText

class RatingActivity : AppCompatActivity() {

    var doctorName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        //Set title of activity
        val b = intent.extras
        if (b != null) doctorName = b.getString("doctor").toString()
        val ratingTitle = findViewById<TextView>(R.id.ratingTitle)
        val text = "Calificá tu experiencia con el <br><strong>Dr. $doctorName<strong>"
        ratingTitle.text = Html.fromHtml(text)

        val loadRating = findViewById<Button>(R.id.loadRating)
        loadRating.isEnabled = false
        val ratingBar = findViewById<RatingBar>(R.id.rating)
        val ratingComment = findViewById<TextInputEditText>(R.id.ratingComment)

        ratingBar.setOnRatingBarChangeListener { _, _, _ ->  run { loadRating.isEnabled = true }}

        loadRating.setOnClickListener {
            //TODO post to backend and redirect to MainActivity
            val rating = "Rating is :" + ratingBar.rating
            val comment = "Comment is :" + ratingComment.text
            Toast.makeText(this, rating, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, comment, Toast.LENGTH_SHORT).show()
        }
    }

}