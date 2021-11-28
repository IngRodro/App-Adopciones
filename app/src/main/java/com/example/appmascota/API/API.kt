package com.example.appmascota.API
import com.example.appmascota.Modelos.*
import retrofit2.Response
import retrofit2.http.*

interface API {

    //Inicio de Sesion
    @POST("User/inicio")
    suspend fun login(
        @Body users: Users
    ): Response<UserResponse>

    @POST("User/save")
    suspend fun saveUser(@Body users: Users)

    @GET("User")
    suspend fun MostrarUser():Response <List<Users>>

    @POST("Mascota/upload")
    suspend fun saveImage(@Body petsUpload: PetsUpload)

    @GET("Mascota/lista")
    suspend fun getImages():Response<List<PetsResponse>>

    @POST("Adopcion/save")
    suspend fun saveAdopcion(@Body adopcion: Adopcion)
}