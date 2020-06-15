package com.conectarSalud.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conectarSalud.R
import com.conectarSalud.adapter.HistoryRVAdapter
import com.conectarSalud.model.HistoryAffiliateItemModel

class AffiliateHistoryFragment: Fragment() {

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
        var historyRv: RecyclerView = view.findViewById(R.id.history_affiliate_recycler_view) as RecyclerView
        var mLayoutManager = LinearLayoutManager(this.activity)
        historyRv.layoutManager = mLayoutManager

        val adapter = context?.let { HistoryRVAdapter(getHistory(), it) }
        historyRv.adapter = adapter

        return view
    }

    private fun getHistory(): ArrayList<HistoryAffiliateItemModel> {
        val histories: ArrayList<HistoryAffiliateItemModel> = ArrayList()
        histories.add(HistoryAffiliateItemModel("Jorge","García", listOf("Medico","Ginecologo"),"01-01-2020"))
        histories.add(HistoryAffiliateItemModel("Pedro","Marmol", listOf("Pediatra"),"01-12-2019"))
        histories.add(HistoryAffiliateItemModel("Jorge","García", listOf("Medico","Ginecologo"),"01-01-2020"))
        histories.add(HistoryAffiliateItemModel("Pedro","Marmol", listOf("Pediatra"),"01-12-2019"))
        histories.add(HistoryAffiliateItemModel("Jorge","García", listOf("Medico","Ginecologo"),"01-01-2020"))
        histories.add(HistoryAffiliateItemModel("Pedro","Marmol", listOf("Pediatra"),"01-12-2019"))
        return histories
    }

}