package com.example.appmascota

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


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
            val user: Users = Users(null,etUser.getText().toString(), etPass.getText().toString(), "","" , "","" );
                IniciarSesion(user)
        }
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun IniciarSesion(user: Users){

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val call = getRetrofit().create(API::class.java).login(user)
                val respuesta = call.body()

            runOnUiThread {
                if (call.isSuccessful) {

                    var estado = respuesta?.estado ?: ""
                    if (estado.equals("Acces")) {
                        if (respuesta != null) {
                            val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                            val editor = prefs.edit()
                            editor.putInt("id", respuesta.id)
                            editor.commit()
                        }
                        showAcces()
                    } else {
                        showNoAcces()
                    }
                } else {

                }
            }
        }catch (e: Exception){

        }
        }
    }


    private fun showNoAcces() {
        Toast.makeText(this,"LOGIN FAILED!!!",Toast.LENGTH_SHORT).show();
    }

    private fun showAcces() {
        val siguienteActivity = Intent(this,InicioActivity::class.java)
        startActivity(siguienteActivity)
    }

}