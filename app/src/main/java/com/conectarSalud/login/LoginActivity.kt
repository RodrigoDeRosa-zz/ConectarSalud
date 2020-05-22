package com.conectarSalud.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.conectarSalud.R
import com.conectarSalud.connector.login.LoginConnector
import com.conectarSalud.helper.RedirectHandler
import com.conectarSalud.services.Resources
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var loginViewModel: LoginViewModel = LoginViewModel(LoginConnector())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val errorMessage = findViewById<TextView>(R.id.loginError)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid
            resetOriginalState()

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            resetOriginalState()

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            else {
                Resources.userLogged = true
                setResult(Activity.RESULT_OK)
                //Complete and destroy login activity once successful
                finish()
                //Redirect to home activity
                val homepage = RedirectHandler().successLoginRedirect(this)
                startActivity(homepage)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(username.text.toString(),password.text.toString())
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(username.text.toString(), password.text.toString())
                }
                false
            }

            loginButton.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        loginError.visibility = View.VISIBLE
        loginError.setText(errorString)
    }

    private fun resetOriginalState() {
        loginError.visibility = View.GONE
        loading.visibility = View.GONE
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
