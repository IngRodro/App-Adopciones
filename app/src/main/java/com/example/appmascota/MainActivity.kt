package com.example.appmascota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


            val user:Users = Users(etUser.getText().toString(), etPass.getText().toString(), "");
            println(user.username + user.password)

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
            val call = getRetrofit().create(SesionUser::class.java).login(user)
            val respuesta = call.body()

            runOnUiThread{
                if(call.isSuccessful){

                    var estado = respuesta?.estado ?: ""
                    println(estado)
                    if(estado.equals("Acces")){
                        showAcces()
                    }else{
                        showNoAcces()
                    }
                }else{

                }
            }
        }
    }

    private fun showNoAcces() {
        Toast.makeText(this,"LOGIN FAILED!!!",Toast.LENGTH_SHORT).show();
    }

    private fun showAcces() {
        Toast.makeText(this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
    }

}