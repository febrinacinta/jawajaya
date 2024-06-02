package com.jawajaya

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class Register : AppCompatActivity() {

    private lateinit var gotoSignUp: TextView
    private lateinit var gotoLogin: Button
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        gotoSignUp = findViewById(R.id.login)
        gotoLogin = findViewById(R.id.loginbutton)

        val buttonClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.login -> {
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                }
                R.id.loginbutton -> {
                    val loginIntent = Intent(this@Register, Login::class.java)
                    startActivity(loginIntent)
                }
            }
        }

        gotoSignUp.setOnClickListener(buttonClickListener)
        gotoLogin.setOnClickListener(buttonClickListener)

        textInputLayout = findViewById(R.id.TextInputLayout)
        textInputEditText = findViewById(R.id.InputPassword)

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val passwordInput = charSequence.toString()
                if (passwordInput.length >= 8) {
                    val pattern = Pattern.compile("[^a-zA-Z0-9]")
                    val matcher = pattern.matcher(passwordInput)
                    val passwordsMatch = matcher.find()
                    if (passwordsMatch) {
                        textInputLayout.helperText = "Password Anda Aman"
                        textInputLayout.error = ""
                    } else {
                        textInputLayout.error = "Password harus berupa campuran karakter angka, huruf dan simbol"
                    }
                } else {
                    textInputLayout.helperText = "Password minimal 8 karakter"
                    textInputLayout.error = ""
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}
