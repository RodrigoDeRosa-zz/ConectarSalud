package com.conectarSalud.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conectarSalud.R
import com.conectarSalud.adapter.HistoryRVAdapter
import com.conectarSalud.connector.rating.ConsultationConnector
import com.conectarSalud.consultation.ConsultationActivity
import com.conectarSalud.consultation.PrescriptionActivity
import com.conectarSalud.interfaces.OnHistoryAffiliateItemClickListener
import com.conectarSalud.model.HistoryAffiliateItemModel
import com.conectarSalud.model.consultation.consultationsDTO
import com.conectarSalud.services.Resources

class AffiliateHistoryFragment: Fragment(), OnHistoryAffiliateItemClickListener {

    private lateinit var emptyConsultationsTxt: TextView
    private var ratingConnector = ConsultationConnector()
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

        val adapter = context?.let { HistoryRVAdapter(getHistory(), it, this) }
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
                        result[i].doctor_specialties?.let { it3 ->
                            result[i].consultation_id?.let { it4 ->
                                result[i].patient_firstname?.let { it5 ->
                                    result[i].patient_lastname?.let { it6 ->
                                        result[i].patient_dni?.let { it7 ->
                                            HistoryAffiliateItemModel(
                                                it, it1, it3, it2, it4, it5, it6, it7
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } }
                    ?.let { histories.add(it) }
            }
            emptyConsultationsTxt.text = ""
        } else {
            emptyConsultationsTxt.text = "Todavía no ha realizado ninguna consulta"
        }
    }

    override fun onItemClicked(history: HistoryAffiliateItemModel) {
        val intent = Intent(this.context, ConsultationActivity::class.java)
        val b = Bundle()
        b.putString("consultationID", history.consultationId)
        intent.putExtras(b)
        startActivity(intent)
    }

}