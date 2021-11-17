package com.example.appmascota
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface SesionUser {
    @POST("User/inicio")
    suspend fun login(
        @Body users: Users
    ): Response<Users>
}