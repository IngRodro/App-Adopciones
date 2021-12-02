package com.example.appmascota.API
import com.example.appmascota.Modelos.*
import retrofit2.Response
import retrofit2.http.*

interface API {

    //Inicio de Sesion
    @POST("User/inicio")
    suspend fun login(@Body users: Users): Response<UserResponse>

    @POST("User/save")
    suspend fun saveUser(@Body users: Users):Response<Boolean>

    @POST("User/update")
    suspend fun updateUser(@Body users: Users)

    @POST("Mascota/upload")
    suspend fun saveMascota(@Body petsRequest: PetsRequest)

    @POST("Mascota/lista")
    suspend fun listaMascotas(@Body users: Users):Response<List<PetsResponse>>

    @POST("Adopcion/save")
    suspend fun saveAdopcion(@Body adopcion: Adopcion)

    @POST("Adopcion/misAdopciones")
    suspend fun listaAdopciones(@Body users: Users):Response<List<Adopcion>>

    @POST("Mascota/misMascotas")
    suspend fun listamisMascotas(@Body users: Users):Response<List<PetsResponse>>

    @DELETE("Mascota/eliminar/{id}")
    suspend fun eliminarMascota(@Path(value = "id") id :Int):Response<Boolean>

    @PUT("Adopcion/adopcionfinalizada/{id}")
    suspend fun finalizarAdopcion(@Path(value = "id")id :Int?):Response<Boolean>

}