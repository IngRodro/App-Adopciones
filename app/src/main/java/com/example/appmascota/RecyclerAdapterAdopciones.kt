package com.example.appmascota

import com.example.appmascota.Modelos.Adopcion
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
import kotlin.collections.ArrayList


private var iduser: Int = 0
class RecyclerAdapterAdopciones : RecyclerView.Adapter<RecyclerAdapterAdopciones.ViewHolder>() {
    var adopciones: List<Adopcion>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(adopciones : List<Adopcion>, context: Context){
        this.adopciones = adopciones
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = adopciones.get(position)
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_adopcion_pets, parent, false))
    }
    override fun getItemCount(): Int {
        return adopciones.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val textName = view.findViewById(R.id.txtPetsNamea) as TextView
        val textEdad = view.findViewById(R.id.txtEdada) as TextView
        val imgPet = view.findViewById(R.id.ivPetsa) as ImageView
        val textRaza:TextView = view.findViewById(R.id.txtPetsRazaa)
        val textidAdopcion: TextView = view.findViewById(R.id.txtidAdopcion)
        val textNContacto: TextView = view.findViewById(R.id.txtNContacto)
        fun bind(adopcion: Adopcion, context: Context){

            textName.text = "Nombre: " + adopcion.idMascota.nombre
            if(adopcion.idMascota.edad ==1){

                textEdad.text = "Edad: " + adopcion.idMascota.edad.toString() + " Año"
            }else{
                textEdad.text = "Edad: " + adopcion.idMascota.edad.toString() + " Años"
            }
            textRaza.text = "Raza: " + adopcion.idMascota.raza
            textidAdopcion.text = "Id Adopcion: " + adopcion.idadopcion
            textNContacto.text = "N de Contacto: " + adopcion.idMascota.iduser.telefono
            val backToBytes: ByteArray = Base64.decode(adopcion.idMascota.fotoString,Base64.URL_SAFE)
            var bmp: Bitmap = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.size )
            imgPet.setImageBitmap(bmp)

        }

    }

}
