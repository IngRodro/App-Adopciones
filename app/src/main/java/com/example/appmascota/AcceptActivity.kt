package com.example.appmascota

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.appmascota.Modelos.PetsResponse

class AcceptActivity : AppCompatActivity() {

    private lateinit var tvNombre: TextView
    private lateinit var tvEdad: TextView
    private lateinit var tvSexo: TextView
    private lateinit var imgMascota: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept)

        var petsResponse : PetsResponse = petsResponsePublic


        tvNombre = findViewById(R.id.tvNombre)
        tvEdad = findViewById(R.id.tvEdad)
        tvSexo = findViewById(R.id.tvSexo)
        imgMascota = findViewById(R.id.imgMascotaSelect)

            tvNombre.setText(petsResponse.nombre)
            tvEdad.setText(petsResponse.edad.toString())
            tvSexo.setText(petsResponse.sexo)

            val backToBytes: ByteArray = Base64.decode(petsResponse.fotoString,Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size );
            imgMascota.setImageBitmap(bmp);

    }
}