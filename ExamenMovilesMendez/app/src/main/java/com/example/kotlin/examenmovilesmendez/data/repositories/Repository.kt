package com.example.kotlin.examenmovilesmendez.data.repositories

import android.util.Log
import com.parse.ParseObject
import com.example.kotlin.examenmovilesmendez.data.network.NetworkModuleDI

class Repository {

    fun consultarEventos(
        page: Int,
        callback: (Result<List<Map<String, Any>>>) -> Unit,
    ) {
        // Generar parámetros para la consulta
        val parametros = createParametros(page)

        // Ejecutar la función en la nube
        NetworkModuleDI.callCloudFunction<HashMap<String, Any>>(
            "hello",
            parametros
        ) { result, e ->
            if (e == null) {
                handleSuccessfulResponse(result, callback)
            } else {
                handleErrorResponse(e, callback)
            }
        }
    }

    private fun createParametros(page: Int): HashMap<String, Any> {
        return hashMapOf("page" to page)
    }

    private fun handleSuccessfulResponse(
        result: HashMap<String, Any>?,
        callback: (Result<List<Map<String, Any>>>) -> Unit,
    ) {
        try {
            val parseObjects = result?.get("data") as? List<ParseObject> ?: emptyList()
            val data = parseObjects.map { it.toMap() }
            callback(Result.success(data))
        } catch (ex: Exception) {
            Log.e("EventosRepository", "Error al procesar la respuesta: ${ex.message}")
            callback(Result.failure(ex))
        }
    }

    private fun handleErrorResponse(
        e: Exception,
        callback: (Result<List<Map<String, Any>>>) -> Unit,
    ) {
        val errorMessage = "Error obteniendo los datos: ${e.localizedMessage}"
        Log.e("EventosRepository", errorMessage, e)
        callback(Result.failure(Exception(errorMessage)))
    }

    private fun ParseObject.toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        for (key in keySet()) {
            map[key] = get(key) ?: ""
        }

        map["objectId"] = this.objectId
        map["createdAt"] = this.createdAt?.toString() ?: ""
        map["updatedAt"] = this.updatedAt?.toString() ?: ""
        map["className"] = this.className

        return map
    }
}
