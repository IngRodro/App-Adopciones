package com.example.appmascota

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmascota.API.APIUser
import com.example.appmascota.Modelos.Pets
import com.example.appmascota.Modelos.PetsResponse
import com.example.appmascota.Modelos.PetsUpload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InicioActivity : AppCompatActivity() {

    var PetsList:MutableList<PetsResponse> = mutableListOf()
    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        CargarLista()
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun CargarLista(){


        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIUser::class.java).getImages()
            val respuesta = call.body()?: emptyList()

            runOnUiThread{
                       for(i in respuesta)  {
                           var petsResponse: PetsResponse = PetsResponse(
                               i.idmascota,
                               i.nombre,
                               i.sexo,
                               i.edad,
                               null,
                               i.fotoString
                           )
                           PetsList.add(petsResponse)
                       }
            }
        }
    }

    fun setUpRecyclerView(view :View){
        mRecyclerView = findViewById(R.id.rvSuperheroList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(PetsList, this)
        mRecyclerView.adapter = mAdapter
    }

}