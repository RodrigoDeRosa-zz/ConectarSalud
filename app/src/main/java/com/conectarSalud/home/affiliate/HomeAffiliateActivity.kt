package com.conectarSalud.home.affiliate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.conectarSalud.R
import com.conectarSalud.fragments.AffiliateHistoryFragment
import com.conectarSalud.fragments.AffiliateHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAffiliateActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_affiliate)

        val fragment = AffiliateHomeFragment.newInstance()
        openFragment(fragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    val fragment = AffiliateHomeFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.page_history -> {
                    val fragment = AffiliateHistoryFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}