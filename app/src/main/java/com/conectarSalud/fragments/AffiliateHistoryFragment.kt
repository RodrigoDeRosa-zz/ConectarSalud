package com.conectarSalud.fragments

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

import com.conectarSalud.R

class AffiliateHistoryFragment: Fragment() {

    companion object {
        fun newInstance(): AffiliateHistoryFragment = AffiliateHistoryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_affiliate_history, container, false)
    }

}