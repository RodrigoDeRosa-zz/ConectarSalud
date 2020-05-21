package com.conectarSalud.services

import com.conectarSalud.model.loginuser.ExtraUserData


object Resources {
    lateinit var consultationID: String
    lateinit var callID: String
    lateinit var dni: String
    var extraUserData: ExtraUserData? = null
    var userLogged = false
}