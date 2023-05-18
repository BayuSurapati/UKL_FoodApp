package com.bayusurapati.ukl_food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById(R.id.btnAfterRegister_activity)

        btnRegister.setOnClickListener{
            val tos = Toast.makeText(this,"ANDA BERHASIL REGISTER",Toast.LENGTH_SHORT)
            tos.show()
        }
    }
}