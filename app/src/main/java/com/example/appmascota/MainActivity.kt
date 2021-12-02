package com.example.appmascota

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
            val user = Users(null,etUser.getText().toString(), etPass.getText().toString(), "","" , "","","")
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
        btnLogin.isEnabled = false
        btnRegister.isEnabled = false
        try {
        CoroutineScope(Dispatchers.IO).launch {

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
                            showAcces()
                        }

                    } else {
                        showNoAcces()
                    }
                } else {
                    btnLogin.isEnabled = true
                    btnRegister.isEnabled = true
                }
            }

        }
        }catch (e: Exception){
            btnLogin.isEnabled = true
            btnRegister.isEnabled = true
        }
    }


    private fun showNoAcces() {
        Toast.makeText(this,"Error de Sesion",Toast.LENGTH_SHORT).show()
        btnLogin.isEnabled = true
        btnRegister.isEnabled = true
    }

    private fun showAcces() {
        finish()
        overridePendingTransition(0, 0)
        val siguienteActivity = Intent(this,InicioActivity::class.java)
        startActivity(siguienteActivity)
        overridePendingTransition(0, 0)
        btnLogin.isEnabled = true
        btnRegister.isEnabled = true
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Estas seguro que deseas cerrar la aplicacion?")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener { dialog, id ->
                    finish()
                    overridePendingTransition(0, 0)

                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->

                })

        var alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}