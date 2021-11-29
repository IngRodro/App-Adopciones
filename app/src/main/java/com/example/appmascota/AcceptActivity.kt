package com.example.appmascota

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept)
        val iduser = intent.extras?.getInt("iduser")

        var petsResponse : PetsResponse = petsResponsePublic


        tvNombre = findViewById(R.id.tvNombre)
        tvEdad = findViewById(R.id.tvEdad)
        tvSexo = findViewById(R.id.tvSexo)
        imgMascota = findViewById(R.id.imgMascotaSelect)
        btnAdopcion = findViewById(R.id.btnAceptarAdopcion)

            tvNombre.setText("Nombre: " + petsResponse.nombre)
            if(petsResponse.edad > 1){

                tvEdad.setText("Edad: " + petsResponse.edad.toString() + " Años")
            }else{
                tvEdad.setText("Edad: " + petsResponse.edad.toString() + " Año")
            }
            tvSexo.setText("Genero: " + petsResponse.sexo)

            val backToBytes: ByteArray = Base64.decode(petsResponse.fotoString,Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size )
            imgMascota.setImageBitmap(bmp)



        btnAdopcion.setOnClickListener {
            if(iduser != null){
                var adopcion = Adopcion(null, petsResponse, Users(iduser, "", "", "", "", "", ""), "Peticion Enviada")
                SaveAdopcion(adopcion)
            }
        }

    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun SaveAdopcion(adopcion: Adopcion){

        try {


            CoroutineScope(Dispatchers.IO).launch {
                getRetrofit().create(API::class.java).saveAdopcion(adopcion)

            }
        }catch (e: Exception){
            println(e)
        }
    }
}