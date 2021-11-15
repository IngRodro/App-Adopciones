package com.example.appmascota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister.setOnClickListener{
            val siguienteActivity = Intent(this,RegisterActivity::class.java)
            startActivity(siguienteActivity)
        }
        etUser = findViewById(R.id.etUser)
        etPass= findViewById(R.id.etPass)

        btnLogin.setOnClickListener{
            if(etUser.getText().toString().equals("admin") && etPass.getText().toString().equals("admin")){
                //correct
                Toast.makeText(this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
            }else
            //incorrect
                Toast.makeText(this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
        }

    }



}