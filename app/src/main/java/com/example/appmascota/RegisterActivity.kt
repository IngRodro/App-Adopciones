package com.example.appmascota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.Users
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterActivity : AppCompatActivity() {
    private lateinit var etNames: EditText
    private lateinit var etLastNames: EditText
    private lateinit var spDepartamento: Spinner
    private lateinit var spMunicipio: Spinner
    private lateinit var etUser: EditText
    private lateinit var etPass: TextInputLayout
    private lateinit var etPassConfirmed: TextInputLayout
    private lateinit var etnPhone: EditText
    private lateinit var btnNewAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etNames = findViewById(R.id.etNames)
        etLastNames = findViewById(R.id.etLastNames)
        spDepartamento = findViewById(R.id.spDepartamento)
        spMunicipio = findViewById(R.id.spMunicipio)
        etUser = findViewById(R.id.etUser)
        etPass = findViewById(R.id.etPass)
        etPassConfirmed = findViewById(R.id.etPassConfirmed)
        etnPhone = findViewById(R.id.etnPhone)
        btnNewAccount = findViewById(R.id.btnNewAccount)


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
        spDepartamento.adapter = adaptador

        //Funcion para recoger un listado de municipios de acuerdo a un departamento
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
        spDepartamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Codigo para cargar el otro spinner
                var depart:String=spDepartamento.selectedItem.toString()
                var Municipios = getMunicipios(depart)
                val adaptadorMuni = ArrayAdapter(applicationContext,  R.layout.item_spin, R.id.textview, Municipios)
               spMunicipio.adapter=adaptadorMuni
            }
        }

        btnNewAccount.setOnClickListener{

            var password = etPass.editText?.text.toString()
            var paswordConfirmed = etPassConfirmed.editText?.text.toString()
            var Nombres = etNames.text.toString()
            var Apellidos = etLastNames.text.toString()
            var Departamento = spDepartamento.selectedItem.toString()
            var Municipio = spMunicipio.selectedItem.toString()
            var Usuario = etUser.text.toString()
            var Telefono = etnPhone.text.toString()


            if(etUser.getText().toString().equals("") || password.equals("") || paswordConfirmed.equals("") || Departamento.equals("Seleccione un Departamento")
                || Municipio.equals("Seleccione un Municipio") || Nombres.equals("") || Apellidos.equals("") || Usuario.equals("") || Telefono.equals("")
            ){
                //correct
                Toast.makeText(this,"Complete todos los campos para continuar",Toast.LENGTH_SHORT).show()
            }else {
                if (!password.equals(paswordConfirmed)){
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }else{
                    val user = Users(null,Usuario, password , Nombres, Apellidos,Departamento,Municipio,Telefono)

                    SaveUser(user)
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

    private fun SaveUser(user: Users){

        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(API::class.java).saveUser(user)
            val respuesta = call.body()

            runOnUiThread {

                if(respuesta ==true){
                    usuarioexistente()
                }else{
                    showAcces()
                }
            }
        }
    }

    private fun showAcces() {

        finish()
        overridePendingTransition(0, 0)
        val siguienteActivity = Intent(this,MainActivity::class.java)
        startActivity(siguienteActivity)
        overridePendingTransition(0, 0)
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        finish()
        overridePendingTransition(0, 0)
        val siguienteActivity = Intent(this,MainActivity::class.java)
        startActivity(siguienteActivity)
        overridePendingTransition(0, 0)
    }

    fun usuarioexistente(){
        Toast.makeText(this,"El Usuario que intentas registrar ya existe, intenta con otro",Toast.LENGTH_SHORT).show()
    }

}