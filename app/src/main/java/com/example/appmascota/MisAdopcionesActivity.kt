package com.example.appmascota

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.Adopcion
import com.example.appmascota.Modelos.PetsResponse
import com.example.appmascota.Modelos.Users
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MisAdopcionesActivity : AppCompatActivity() {

    lateinit var mRecyclerView : RecyclerView
    lateinit var drawerLayout: DrawerLayout
    val mAdapter : RecyclerAdapterAdopciones = RecyclerAdapterAdopciones()
    var Adopciones:MutableList<Adopcion> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_adopciones)

        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        val iduser = prefs.getInt("id", 0)

        CargarLista(iduser)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)))
        navigationView.setBackgroundColor(getResources().getColor(R.color.purple))
        val inicioActivity = Intent(this, InicioActivity::class.java)
        val registerPetActivity = Intent(this, PetRegisterActivity::class.java)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                //R.id.nav_home -> startActivity(inicioActivity)
                //R.id.nav_message ->
                R.id.nav_login -> Toast.makeText(
                    this,
                    "Login is Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_share -> Toast.makeText(
                    this,
                    "Share is clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_petRegister -> {
                    val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("id", iduser)
                    editor.commit()
                    startActivity(registerPetActivity)
                }

            }
            false
        })
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("https://app-mascotas-programacion-iv.herokuapp.com/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun CargarLista(iduser: Int){

        Adopciones.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(API::class.java).listaAdopciones(Users(iduser, "", "", "", "", "", "",""))
            val respuesta = call.body()?: emptyList()

            runOnUiThread{
                for(i in respuesta)  {
                    var adopcion: Adopcion = Adopcion(
                        i.idadopcion,
                        i.idMascota,
                        i.idUsuarioAdopta,
                        i.estado)
                    Adopciones.add(adopcion)
                }

                setUpRecyclerView()
            }
        }
    }

    fun setUpRecyclerView(){
        mRecyclerView = findViewById(R.id.rvPetsAdoptList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(Adopciones, this)
        mRecyclerView.adapter = mAdapter
    }
}