package com.example.appmascota

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.Adopcion
import com.example.appmascota.Modelos.PetsResponse
import com.example.appmascota.Modelos.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class AcceptActivity : AppCompatActivity() {

    private lateinit var tvNombre: TextView
    private lateinit var tvEdad: TextView
    private lateinit var tvSexo: TextView
    private lateinit var imgMascota: ImageView
    private lateinit var btnAdopcion: Button
    private lateinit var tvRaza : TextView
    var iduser: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept)
        val iduser = intent.extras?.getInt("iduser")
        if(iduser != null) {
            this.iduser = iduser
        }
        var petsResponse : PetsResponse = petsResponsePublic


        tvNombre = findViewById(R.id.tvNombre)
        tvEdad = findViewById(R.id.tvEdad)
        tvSexo = findViewById(R.id.tvSexo)
        imgMascota = findViewById(R.id.imgMascotaSelect)
        btnAdopcion = findViewById(R.id.btnAceptarAdopcion)
        tvRaza = findViewById(R.id.tvRaza)

            tvNombre.setText("Nombre: " + petsResponse.nombre)
            if(petsResponse.edad > 1){

                tvEdad.setText("Edad: " + petsResponse.edad.toString() + " Años")
            }else{
                tvEdad.setText("Edad: " + petsResponse.edad.toString() + " Año")
            }
            tvSexo.setText("Genero: " + petsResponse.sexo)
            tvRaza.setText("Raza: " + petsResponse.raza)

            val backToBytes: ByteArray = Base64.decode(petsResponse.fotoString,Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size )
            imgMascota.setImageBitmap(bmp)



        btnAdopcion.setOnClickListener {
            if(iduser != null){
                var adopcion = Adopcion(null, petsResponse, Users(iduser, "", "", "", "", "", "",""), "Peticion Enviada")
                SaveAdopcion(adopcion)
            }
        }

    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun SaveAdopcion(adopcion: Adopcion){
        btnAdopcion.isEnabled = false
        try {
            CoroutineScope(Dispatchers.IO).launch {
               getRetrofit().create(API::class.java).saveAdopcion(adopcion)
                val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("id", iduser)
                editor.commit()
                inicio()
            }
        }catch (e: Exception){
            btnAdopcion.isEnabled = true
        }
    }
    private fun inicio(){
        val siguienteActivity = Intent(this,InicioActivity::class.java)
        startActivity(siguienteActivity)
    }
    override fun onBackPressed() {
        inicio()
    }


}