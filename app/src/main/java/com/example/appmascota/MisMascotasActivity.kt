package com.example.appmascota

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmascota.API.API
import com.example.appmascota.Modelos.PetsResponse
import com.example.appmascota.Modelos.Users
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MisMascotasActivity : AppCompatActivity() {
    lateinit var mRecyclerView : RecyclerView
    lateinit var drawerLayout: DrawerLayout
    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    var PetsMutableList:MutableList<PetsResponse> = mutableListOf()
    var iduser: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_mascotas)
        val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
        iduser = prefs.getInt("id", 0)


        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)))
        navigationView.setBackgroundColor(getResources().getColor(R.color.purple))
        val inicioActivity = Intent(this,InicioActivity::class.java)
        val registerPetActivity = Intent(this, PetRegisterActivity::class.java)
        val adopcionesActivity = Intent(this, MisAdopcionesActivity::class.java)
        val mismascotasActivity = Intent(this, MisMascotasActivity::class.java)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.nav_home -> {
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(inicioActivity)
                    overridePendingTransition(0, 0)
                }
                R.id.nav_mypets -> {
                    val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("id", iduser)
                    editor.commit()
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(mismascotasActivity)
                    overridePendingTransition(0, 0)}
                R.id.nav_close ->{
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Estas seguro que deseas cerrar sesion?")
                        .setPositiveButton("Si",
                            DialogInterface.OnClickListener { dialog, id ->
                                finish()
                                overridePendingTransition(0, 0)
                                val siguienteActivity = Intent(this,MainActivity::class.java)
                                startActivity(siguienteActivity)
                                overridePendingTransition(0, 0)
                            })
                        .setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog, id ->

                            })

                    var alertDialog: AlertDialog = builder.create()
                    alertDialog.show()
                }
                R.id.nav_adopciones -> {
                    val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("id", iduser)
                    editor.commit()
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(adopcionesActivity)
                    overridePendingTransition(0, 0)
                }
                R.id.nav_petRegister ->{
                    val prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putInt("id", iduser)
                    editor.commit()
                    finish()
                    startActivity(registerPetActivity)
                    overridePendingTransition(0, 0)}

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

        PetsMutableList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(API::class.java).listamisMascotas(Users(iduser, "","","","","","",""))
            val respuesta = call.body()?: emptyList()

            runOnUiThread{
                for(i in respuesta)  {
                    var petsResponse: PetsResponse = PetsResponse(
                        i.idmascota,
                        i.nombre,
                        i.sexo,
                        i.edad,
                        i.raza,
                        i.fotoString,
                        i.iduser,
                        i.estado,
                        i.idAdopcion)
                    PetsMutableList.add(petsResponse)
                }

                setUpRecyclerView(iduser)
            }
        }
    }

    fun setUpRecyclerView(iduser: Int){
        mRecyclerView = findViewById(R.id.rvmisPetsList) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(PetsMutableList, this, iduser, true)
        mRecyclerView.adapter = mAdapter
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
}