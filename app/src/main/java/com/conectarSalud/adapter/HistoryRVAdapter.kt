package com.conectarSalud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectarSalud.R
import com.conectarSalud.interfaces.OnHistoryAffiliateItemClickListener
import com.conectarSalud.model.HistoryAffiliateItemModel


class HistoryRVAdapter(val histories : ArrayList<HistoryAffiliateItemModel>,
                       val context: Context,
                       val listener: OnHistoryAffiliateItemClickListener ) : RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder?>() {

    class HistoryViewHolder internal constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var historyClicked: HistoryAffiliateItemModel

        var medicName: TextView
        var specialities: TextView
        var date: TextView

        init {
            medicName = itemView.findViewById(R.id.medic_name)
            specialities = itemView.findViewById(R.id.specialities)
            date = itemView.findViewById(R.id.date)
        }

        fun bind(history: HistoryAffiliateItemModel,clickListener: OnHistoryAffiliateItemClickListener)
        {
            historyClicked = history

            itemView.setOnClickListener {
                clickListener.onItemClicked(history)
            }
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var history = histories?.get(position)
        holder.medicName.text = (history?.medicName ?: "none") +" "+ (history?.medicLastName?: "none")
        holder.specialities.text = history?.specialities?.joinToString(", ") ?: "none"
        holder.date.text = "Atendido el "+history?.date
        holder.bind(histories[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_affiliate_history, parent, false)
        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return this.histories?.size!!;
    }
}