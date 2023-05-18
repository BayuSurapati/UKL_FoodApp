package com.bayusurapati.ukl_food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin_activity)
        btnLogin.setOnClickListener(this)

        btnRegister = findViewById(R.id.btnRegister_activity)
        btnRegister.setOnClickListener(this)

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin_activity -> {
                btnLogin.setOnClickListener {
                    val inputEmail = findViewById<EditText>(R.id.editTextEmail).text.toString()
                    val inputPassword =
                        findViewById<EditText>(R.id.editTextPassword).text.toString()

                    Intent(this, SplashActivity::class.java).also {
                        it.putExtra("EXTRA_EMAIL", inputEmail)
                        val inputEmail = editTextEmail.text.toString()
                        val inputPassword = editTextPassword.text.toString()

                        if (inputEmail.isEmpty()) {
                            editTextEmail.setError("Masukkan Email Anda")
                        } else if (inputPassword.isEmpty()) {
                            editTextPassword.setError("Masukkan Password Anda")
                        } else {
                            startActivity(it)
                        }
                    }
                }
            }
            R.id.btnRegister_activity->{
                val moveIntentRegister = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(moveIntentRegister)
            }
        }
    }
}