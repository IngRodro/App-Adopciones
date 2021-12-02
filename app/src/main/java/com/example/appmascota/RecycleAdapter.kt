package com.example.appmascota

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.PetsRequest
import com.example.appmascota.Modelos.PetsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList
import androidx.core.content.ContextCompat.startActivity




lateinit var petsResponsePublic: PetsResponse
private var iduser: Int = 0
private var booleanp: Boolean = false
class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var petsList: List<PetsResponse> = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(pets: List<PetsResponse>, context: Context, Iduser: Int, boolean: Boolean) {
        this.petsList = pets
        this.context = context
        iduser = Iduser
        booleanp = boolean
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = petsList.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pets, parent, false))
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val textName = view.findViewById(R.id.txtPetsName) as TextView
        val textEdad = view.findViewById(R.id.txtEdad) as TextView
        val textSexo = view.findViewById(R.id.txtSexo) as TextView
        val imgPet = view.findViewById(R.id.ivPets) as ImageView
        val textRaza: TextView = view.findViewById(R.id.txtPetsRaza)
        val textEstado: TextView = view.findViewById(R.id.txtPetsState)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
        val btnEntregar: Button = view.findViewById(R.id.btnConfirmarEntrega)
        val textidAdopcion: TextView = view.findViewById(R.id.txtPetsidAdopcion)
        fun bind(petsResponse: PetsResponse, context: Context) {

            textName.text = "Nombre: " + petsResponse.nombre
            if(petsResponse.edad == 0){
                textEdad.text = "Edad: Menos de un Año"
            } else if (petsResponse.edad == 1) {

                textEdad.text = "Edad: " + petsResponse.edad.toString() + " Año"
            } else {
                textEdad.text = "Edad: " + petsResponse.edad.toString() + " Años"
            }
            textSexo.text = "Genero: " + petsResponse.sexo
            textRaza.text = "Raza: " + petsResponse.raza
            val backToBytes: ByteArray = Base64.decode(petsResponse.fotoString, Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size)
            imgPet.setImageBitmap(bmp)
            if(booleanp == true){
                textSexo.text = ""
                if(petsResponse.estado == null){
                    textEstado.text = ""

                }else{
                    textEstado.text = "Estado: " + petsResponse.estado
                    btnEliminar.isVisible = false
                    btnEntregar.isVisible = true

                    if(petsResponse.idAdopcion != null){
                        textidAdopcion.text = "Id Adopcion: " + petsResponse.idAdopcion.toString()
                    }
                }
            }else{
                textEstado.text = ""
                btnEliminar.isVisible = false
                itemView.setOnClickListener(View.OnClickListener {

                    petsResponsePublic = petsResponse
                    val siguienteActivity = Intent(context, AcceptActivity::class.java)
                    siguienteActivity.putExtra("iduser", iduser)
                    startActivity(context, siguienteActivity, null)

                })
            }

            btnEliminar.setOnClickListener {

                    try {

                        CoroutineScope(Dispatchers.IO).launch {
                           var call = getRetrofit().create(API::class.java).eliminarMascota(petsResponse.idmascota)

                            if(call.body() == true){

                                    val i = Intent(context, MisMascotasActivity::class.java)
                                    context.startActivity(i)

                            }else{

                            }

                        }
                    }catch (e: Exception){

                    }

            }

            btnEntregar.setOnClickListener {
                try {
                    btnEntregar.isEnabled = false
                    CoroutineScope(Dispatchers.IO).launch {
                        var call = getRetrofit().create(API::class.java).finalizarAdopcion(petsResponse.idAdopcion)
                        println(call.body())
                        if(call.body() == true){

                            val i = Intent(context, MisMascotasActivity::class.java)
                            context.startActivity(i)

                        }else{

                        }

                    }
                }catch (e: Exception){
                    println(e)
                    btnEntregar.isEnabled = true
                }
            }

        }


    }
}

private  fun getRetrofit(): Retrofit {
    return  Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/APIMascotas/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

