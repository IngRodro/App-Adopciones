package com.example.appmascota

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserSettingsActivity : AppCompatActivity() {

    private lateinit var spEditDepartamento: Spinner
    private lateinit var spEditMunicipio: Spinner
    private lateinit var btnActualizar: Button
    private lateinit var txtTelefono: EditText
    var iduser:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        spEditDepartamento = findViewById(R.id.sp_EditDepartamento)
        spEditMunicipio= findViewById(R.id.sp_EditMunicipio)
        btnActualizar = findViewById(R.id.btnActualizar)
        txtTelefono = findViewById(R.id.et_EditPhone)

        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        iduser = prefs.getInt("id", 0)

        val departamentos = arrayOf(
            "Seleccione un Departamento",
            "San Salvador",
            "Chalatenango",
            "Sonsonate",
            "La Paz",
            "La Libertad",
            "San Miguel",
            "Usulután",
            "Cabañas",
            "Santa Ana",
            "San Vicente",
            "La Unión",
            "Morazán",
            "Cuscatlán",
            "Ahuachapán"
        )

        val adaptador = ArrayAdapter(this, R.layout.item_spin, R.id.textview, departamentos)
        spEditDepartamento.adapter = adaptador

        fun getMunicipios(departamento:String):ArrayList<String>{
            val municipios= arrayListOf<String>()
            when(departamento){
                "Seleccione un Departamento"->municipios.addAll(listOf("Seleccione un Municipio"))
                "Chalatenango"->municipios.addAll(listOf("Seleccione un Municipio","Agua Caliente","Arcatao","Azacualpa","Chalatenango","Citalá","Comalapa","Concepción Quezaltepeque",
                    "Dulce Nombre de María","El Carrizal","El Paraíso","La Laguna","La Palma","La Reina","Las Vueltas","Nombre de Jesús","Nueva Concepción",
                    "Nueva Trinidad","Ojos de Agua","Potonico","San Antonio de la Cruz","San Antonio Los Ranchos","San Fernando","San Francisco Lempa",
                    "San Francisco Morazán","San Ignacio","San Isidro Labrador","San José Cancasque (Cancasque)","San José Las Flores","San Luis del Carmen",
                    "San Miguel de Mercedes","San Rafael","Santa Rita","Tejutla"))
                "San Salvador"->municipios.addAll(listOf("Seleccione un Municipio","Aguilares","Apopa","Ayutuxtepeque","Ciuddad Delgado","Cuscatancingo","El Paisnal","Guazapa","Ilopango",
                    "Mejicanos","Nejapa","Panchimalco","Rosario de Mora","San Marcos","San Martín","San Salvador","Santiago Texacuangos","Santo Tomás",
                    "Soyapango","Tonacatepeque"))
                "Sonsonate"->municipios.addAll(listOf("Seleccione un Municipio","Acajutla","Armenia","Caluco","Cuisnahuat","Izalco","Juayúa","Nahuizalco","Nahulingo","Salcoatitán",
                    "San Antonio del Monte","San Julián","Santa Catarina Masahuat","Santa Isabel Ishuatán","Santo Domingo de Guzmán","Sonsonate",
                    "Sonzacate"))
                "La Paz"->municipios.addAll(listOf("Seleccione un Municipio","Cuyultitán","El Rosario (Rosario de La Paz)","Jerusalén","Mercedes La Ceiba","Olocuilta","Paraíso de Osorio",
                    "San Antonio Masahuat","San Emigdio","San Francisco Chinameca","San Juan Nonualco","San Juan Talpa","San Juan Tepezontes",
                    "San Luis La Herradura","San Luis Talpa","San Miguel Tepezontes","San Pedro Masahuat","San Pedro Nonualco","San Rafael Obrajuelo",
                    "Santa María Ostuma","Santiago Nonualco","Tapalhuaca","Zacatecoluca"))
                "La Libertad"->municipios.addAll(listOf("Seleccione un Municipio","Antiguo Cuscatlán","Chiltiupán","Ciudad Arce","Colón","Comasagua","Huizúcar","Jayaque","Jicalapa",
                    "La Libertad","Santa Tecla (Nueva San Salvador)","Nuevo Cuscatlán","San Juan Opico","Quezaltepeque","Sacacoyo",
                    "San José Villanueva","San Matías","San Pablo Tacachico","Talnique","Tamanique","Teotepeque","Tepecoyo","Zaragoza"))
                "San Miguel"->municipios.addAll(listOf("Seleccione un Municipio","Carolina","Chapeltique","Chinameca","Chirilagua","Ciudad Barrios","Comacarán","El Tránsito","Lolotique",
                    "Moncagua","Nueva Guadalupe","Nuevo Edén de San Juan","Quelepa","San Antonio del Mosco","San Gerardo","San Jorge",
                    "San Luis de la Reina","San Miguel","San Rafael Oriente","Sesori","Uluazapa"))
                "Usulután"->municipios.addAll(listOf("Seleccione un Municipio","Alegría","Berlín","California","Concepción Batres","El Triunfo","Ereguayquín","Estanzuelas","Jiquilisco",
                    "Jucuapa","Jucuarán","Mercedes Umaña","Nueva Granada","Ozatlán","Puerto El Triunfo","San Agustín","San Buenaventura",
                    "San Dionisio","San Francisco Javier","Santa Elena","Santa María","Santiago de María","Tecapán","Usulután"))
                "Cabañas"->municipios.addAll(listOf("Seleccione un Municipio","Cinquera","Dolores (Villa Doleres)","Guacotecti","Ilobasco","Jutiapa","San Isidro",
                    "Sensuntepeque","Tejutepeque","Victoria"))
                "Santa Ana"->municipios.addAll(listOf("Seleccione un Municipio","Candelaria de la Frontera","Chalchuapa","Coatepeque","El Congo","El Porvenir","Masahuat","Metapán",
                    "San Antonio Pajonal","San Sebastián Salitrillo","Santa Ana","Santa Rosa Guachipilín","Santiago de la Frontera","Texistepeque"))
                "San Vicente"->municipios.addAll(listOf("Seleccione un Municipio","Apastepeque","Guadalupe","San Cayetano Istepeque","San Esteban Catarina","San Ildefonso","San Lorenzo",
                    "San Sebastián","San Vicente","Santa Clara","Santo Domingo","Tecoluca","Tepetitán","Verapaz"))
                "La Unión"->municipios.addAll(listOf("Seleccione un Municipio","Anamorós","Bolívar","Concepción de Oriente","Conchagua","El Carmen","El Sauce","Intipucá","La Unión","Lilisque",
                    "Meanguera del Golfo","Nueva Esparta","Pasaquina","Polorós","San Alejo","San José","Santa Rosa de Lima","Yayantique","Yucuaiquín"))
                "Morazán"->municipios.addAll(listOf("Seleccione un Municipio","Arambala","Cacaopera","Chilanga","Corinto","Delicias de Concepción","El Divisadero","El Rosario","Gualococti",
                    "Guatajiagua","Joateca","Jocoaitique","Jocoro","Lolotiquillo","Meanguera","Osicala","Perquín","San Carlos","San Fernando",
                    "San Francisco Gotera","San Isidro","San Simón","Sensembra","Sociedad","Torola","Yamabal","Yoloaiquín"))
                "Cuscatlán"->municipios.addAll(listOf("Seleccione un Municipio","Candelaria","Cojutepeque","El Carmen","El Rosario","Monte San Juan","Oratorio de Concepción","San Bartolomé Perulapía",
                    "San Cristóbal","San José Guayabal","San Pedro Perulapán","San Rafael Cedros","San Ramón","Santa Cruz Analquito","Santa Cruz Michapa",
                    "Suchitoto","Tenancingo"))
                "Ahuachapán"->municipios.addAll(listOf("Seleccione un Municipio","Ahuachapán","Apaneca","Atiquizaya","Concepción de Ataco","El Refugio","Guaymango","Jujutla",
                    "San Francisco Menéndez","San Lorenzo","San Pedro Puxtla","Tacuba","Turín"))
            }
            return municipios
        }


        //Esta parte simula el evento ItemChange de una lista
        spEditDepartamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Codigo para cargar el otro spinner
                var depart:String=spEditDepartamento.selectedItem.toString()
                var Municipios = getMunicipios(depart)
                val adaptadorMuni = ArrayAdapter(applicationContext,  R.layout.item_spin, R.id.textview, Municipios)
                spEditMunicipio.adapter=adaptadorMuni
            }
        }

        btnActualizar.setOnClickListener {

            var Departamento = spEditDepartamento.selectedItem.toString()
            var Municipio = spEditMunicipio.selectedItem.toString()
            if(txtTelefono.equals("") && Municipio.equals("Seleccione un Municipio") && Departamento.equals("Seleccione un Departamento")){
                guardarajuste()
                Toast.makeText(this, "No se realizaron cambios", Toast.LENGTH_SHORT).show()
            }else{

                if(Municipio.equals("Seleccione un Municipio") && !Departamento.equals("Seleccione un Departamento")){
                    Toast.makeText(this, "Seleccione un Municipio para actualizar su  ubicacion", Toast.LENGTH_SHORT).show()
                }else{
                    UpdateUser(Users(iduser, "", "", "", "", Departamento, Municipio, txtTelefono.text.toString()))
                    guardarajuste()
                    Toast.makeText(this, "Cambios realizados con exito", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun UpdateUser(user: Users){

        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(API::class.java).updateUser(user)

        }
    }

    override fun onBackPressed() {
        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("id", iduser)
        editor.commit()
        finish()
        overridePendingTransition(0, 0)
        val siguienteActivity = Intent(this,InicioActivity::class.java)
        startActivity(siguienteActivity)
        overridePendingTransition(0, 0)
    }

    fun guardarajuste(){
        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("id", iduser)
        editor.commit()
        finish()
        overridePendingTransition(0, 0)
        val siguienteActivity = Intent(this,InicioActivity::class.java)
        startActivity(siguienteActivity)
        overridePendingTransition(0, 0)
    }

}