package com.conectarSalud.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conectarSalud.R
import com.conectarSalud.adapter.HistoryRVAdapter
import com.conectarSalud.connector.rating.RatingConnector
import com.conectarSalud.model.HistoryAffiliateItemModel
import com.conectarSalud.model.consultation.consultationsDTO

class AffiliateHistoryFragment: Fragment() {

    private lateinit var emptyConsultationsTxt: TextView
    private var ratingConnector = RatingConnector()
    private var histories: ArrayList<HistoryAffiliateItemModel> = ArrayList()

    companion object {
        fun newInstance(): AffiliateHistoryFragment = AffiliateHistoryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(
            com.conectarSalud.R.layout.fragment_affiliate_history,
            container, false
        )

        emptyConsultationsTxt = view.findViewById(R.id.empty_consultations_txt) as TextView

        var historyRv: RecyclerView = view.findViewById(R.id.history_affiliate_recycler_view) as RecyclerView
        var mLayoutManager = LinearLayoutManager(this.activity)
        historyRv.layoutManager = mLayoutManager

        val adapter = context?.let { HistoryRVAdapter(getHistory(), it) }
        historyRv.adapter = adapter

        return view
    }

    private fun getHistory(): ArrayList<HistoryAffiliateItemModel> {
        this.ratingConnector.getConsultations( ::setHistoryAfterRequest )
        return histories
    }

    private fun setHistoryAfterRequest(result: ArrayList<consultationsDTO>?) {
        if (result != null && result.isNotEmpty()) {
            for (i in 0 until result.size) {
                result[i].doctor_firstname?.let { result[i].doctor_lastname?.let { it1 ->
                    result[i].date?.let { it2 ->
                        HistoryAffiliateItemModel(it,
                            it1, listOf("Medico","Ginecologo"), it2
                        )
                    }
                } }
                    ?.let { histories.add(it) }
            }
            emptyConsultationsTxt.text = ""
        } else {
            emptyConsultationsTxt.text = "Todavía no ha realizado ninguna consulta"
        }
    }

}