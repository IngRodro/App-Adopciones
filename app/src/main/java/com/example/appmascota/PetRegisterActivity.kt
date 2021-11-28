package com.example.appmascota

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.appmascota.Modelos.PetsUpload
import android.graphics.Bitmap
import com.example.appmascota.API.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import android.graphics.drawable.BitmapDrawable





class PetRegisterActivity : AppCompatActivity() {
    private var REQUEST_GALLERY = 1001
    private lateinit var btnSeleccionar: Button
    private lateinit var btnSaveImage: Button
    private lateinit var imgMascota: ImageView
    private lateinit var etPetName :EditText
    private lateinit var etPetAge :EditText
    private lateinit var etPetSex :EditText
    private lateinit var etPetRaza :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_register)
        btnSeleccionar = findViewById(R.id.btnSeleccionar)
        btnSaveImage = findViewById(R.id.btnSaveImage)
        imgMascota = findViewById(R.id.imgMascota)
        etPetName = findViewById(R.id.etPetName)
        etPetAge = findViewById(R.id.etPetAge)
        etPetSex = findViewById(R.id.etPetSex)
        etPetRaza = findViewById(R.id.etPetRaza)
        btnSeleccionar.setOnClickListener{
            cargarImagen()
        }

        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        val iduser = prefs.getInt("id", 0)
        btnSaveImage.setOnClickListener{

            val bitmap = (imgMascota.getDrawable() as BitmapDrawable).getBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

            var imageinByte: ByteArray = stream.toByteArray()


            var pet: PetsUpload = PetsUpload(etPetName.getText().toString(), etPetSex.getText().toString(), etPetAge.getText().toString().toInt(), etPetRaza.getText().toString(), imageinByte, iduser)
            SaveImage(pet)
        }
    }


    fun cargarImagen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permisosArchivos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permisosArchivos, REQUEST_GALLERY)
            }else{
                MuestraGaleria()
            }
        }else{
            MuestraGaleria()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALLERY ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    MuestraGaleria()
                }else{
                    //Permiso no existe
                }
            }
        }
    }

    private fun MuestraGaleria() {
        val intentGaleria = Intent(Intent.ACTION_PICK)
        intentGaleria.type = "image/*"
        startActivityForResult(intentGaleria, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY){
            imgMascota.setImageURI(data?.data)
        }
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun SaveImage(petsUpload: PetsUpload){

        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(API::class.java).saveImage(petsUpload)
        }
    }

}