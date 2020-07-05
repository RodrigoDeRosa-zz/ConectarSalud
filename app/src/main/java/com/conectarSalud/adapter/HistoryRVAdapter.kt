package com.conectarSalud.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectarSalud.R
import com.conectarSalud.consultation.ConsultationActivity
import com.conectarSalud.interfaces.OnHistoryAffiliateItemClickListener
import com.conectarSalud.model.HistoryAffiliateItemModel
import org.w3c.dom.Text


class HistoryRVAdapter(val histories : ArrayList<HistoryAffiliateItemModel>,
                       val context: Context,
                       val listener: OnHistoryAffiliateItemClickListener ) : RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder?>() {

    class HistoryViewHolder internal constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var historyClicked: HistoryAffiliateItemModel

        var medicName: TextView
        var affiliateName: TextView
        var specialities: TextView
        var date: TextView
        var button: Button

        init {
            medicName = itemView.findViewById(R.id.medic_name)
            affiliateName = itemView.findViewById(R.id.affiliate_name)
            specialities = itemView.findViewById(R.id.specialities)
            date = itemView.findViewById(R.id.date)
            button = itemView.findViewById(R.id.loadConsultation)
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var history = histories?.get(position)
        holder.medicName.text = (history?.medicName ?: "none") +" "+ (history?.medicLastName?: "none")
        holder.affiliateName.text = (history?.affiliateName ?: "none") +" "+ (history?.affiliateLastName?: "none")
        holder.specialities.text = history?.specialities?.joinToString(", ") ?: "none"
        holder.date.text = "Atendido el "+history?.date

        holder.button.setOnClickListener {
            val intent = Intent(context, ConsultationActivity::class.java)
            val b = Bundle()
            b.putString("consultationID", history.consultationId)
            intent.putExtras(b)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_affiliate_history, parent, false)
        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return this.histories?.size!!;
    }
}