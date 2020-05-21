package com.conectarSalud.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.home.medic.HomeMedicActivity
import com.conectarSalud.services.Resources

class RedirectHandler() {

    fun successLoginRedirect(activity: AppCompatActivity): Intent {
        if (Resources.extraUserData?.role == "affiliate") {
            return Intent(activity, HomeAffiliateActivity::class.java)
        }
        return Intent(activity, HomeMedicActivity::class.java)
    }
}