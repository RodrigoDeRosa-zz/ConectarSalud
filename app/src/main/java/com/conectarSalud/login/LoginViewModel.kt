package com.conectarSalud.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.conectarSalud.connector.login.LoginConnector

import com.conectarSalud.R
import com.conectarSalud.helper.sha256
import com.conectarSalud.model.state.LoginFormState
import com.conectarSalud.model.loginuser.LoginResult
import com.conectarSalud.services.Resources
import java.util.regex.Pattern.matches

class LoginViewModel(private val loginConnector: LoginConnector) : ViewModel() {

    // observer pattern needs LiveData
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        Resources.userID = username
        loginConnector.login(username, password.sha256(), ::setLoginAfterRequest)
    }

    private fun setLoginAfterRequest(result: LoginResult) {
        _loginResult.value = result
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return matches("\\d*", username)
    }

    // placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length < 31
    }
}
