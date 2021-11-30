package com.example.appmascota.Modelos

data class PetsResponse (var idmascota:Int,var nombre:String, var sexo:String, var edad: Int,var raza:String, var fotoString: String?, var iduser:Users, var estado: String?, var idAdopcion: Int?) {

}