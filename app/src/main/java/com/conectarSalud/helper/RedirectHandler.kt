package com.conectarSalud.helper

import android.content.Intent
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.home.medic.HomeMedicActivity
import com.conectarSalud.login.LoginActivity

class RedirectHandler() {

    fun successLoginRedirect(loginActivity: LoginActivity): Intent {
        if (loginActivity.loginViewModel.loginResult.value?.userData?.role == "affiliate") {
            return Intent(loginActivity, HomeAffiliateActivity::class.java)
        }
        return Intent(loginActivity, HomeMedicActivity::class.java)
    }
}