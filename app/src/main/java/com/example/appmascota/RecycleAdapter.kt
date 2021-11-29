package com.example.appmascota

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appmascota.Modelos.PetsResponse
import kotlin.collections.ArrayList

public lateinit var petsResponsePublic: PetsResponse
private var iduser: Int = 0
class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var petsList: List<PetsResponse>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(pets : List<PetsResponse>, context: Context, Iduser: Int){
        this.petsList = pets
        this.context = context
        iduser = Iduser
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
        val textRaza:TextView = view.findViewById(R.id.txtPetsRaza)
        fun bind(petsResponse: PetsResponse, context: Context){

            textName.text = "Nombre: " + petsResponse.nombre
            if(petsResponse.edad ==1){

                textEdad.text = "Edad: " + petsResponse.edad.toString() + " Año"
            }else{
                textEdad.text = "Edad: " + petsResponse.edad.toString() + " Años"
            }
            textSexo.text = "Genero: " + petsResponse.sexo
            textRaza.text = "Raza: " + petsResponse.raza
            val backToBytes: ByteArray = Base64.decode(petsResponse.fotoString,Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size );
            imgPet.setImageBitmap(bmp);

            itemView.setOnClickListener(View.OnClickListener {

                petsResponsePublic = petsResponse
                val siguienteActivity = Intent(context,AcceptActivity::class.java)
                siguienteActivity.putExtra("iduser", iduser)
                startActivity(context,siguienteActivity,null)
            })
        }

    }
}
