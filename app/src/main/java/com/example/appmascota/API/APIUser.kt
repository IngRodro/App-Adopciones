package com.example.appmascota.API
import com.example.appmascota.Modelos.Pets
import com.example.appmascota.Modelos.UserResponse
import com.example.appmascota.Modelos.Users
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface APIUser {

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
    suspend fun saveImage(@Body pets: Pets)
}