package com.example.appmascota.Modelos

data class PetsRequest(var nombre:String, var sexo:String, var edad: Int, var raza:String, var foto: ByteArray?, var iduser: Users) {

}