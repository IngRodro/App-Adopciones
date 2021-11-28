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
import com.example.appmascota.Modelos.PetsResponse
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InicioActivity : AppCompatActivity() {



    lateinit var mRecyclerView : RecyclerView
    lateinit var drawerLayout: DrawerLayout
    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    var PetsMutableListPublic:MutableList<PetsResponse> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        val iduser = prefs.getInt("id", 0)


        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)))
        navigationView.setBackgroundColor(getResources().getColor(R.color.purple))
        val inicioActivity = Intent(this,InicioActivity::class.java)
        val registerPetActivity = Intent(this, PetRegisterActivity::class.java)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                //R.id.nav_home -> startActivity(inicioActivity)
                //R.id.nav_message ->
                R.id.synch -> Toast.makeText(
                    this,
                    "iduser.toString()",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.trash -> Toast.makeText(
                    this,
                    "Trash is Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.settings -> Toast.makeText(
                    this,
                    "Settings is Clicked",
                    Toast.LENGTH_SHORT
                ).show()
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
                R.id.nav_petRegister ->{
                    val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("id", iduser)
                    editor.commit()
                    startActivity(registerPetActivity)}

            }
            false
        })
        CargarLista(iduser)
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/APIMascotas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun CargarLista(iduser: Int){

        PetsMutableListPublic.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(API::class.java).getImages()
            val respuesta = call.body()?: emptyList()

            runOnUiThread{
                for(i in respuesta)  {
                    var petsResponse: PetsResponse = PetsResponse(
                        i.idmascota,
                        i.nombre,
                        i.sexo,
                        i.edad,
                        i.raza,
                        i.fotoString)
                    PetsMutableListPublic.add(petsResponse)
                }

                setUpRecyclerView(iduser)
            }
        }
    }

    fun setUpRecyclerView(iduser: Int){
        mRecyclerView = findViewById(R.id.rvPetsList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(PetsMutableListPublic, this, iduser)
        mRecyclerView.adapter = mAdapter
    }

}