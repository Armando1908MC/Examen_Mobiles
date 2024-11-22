package com.example.kotlin.examenmovilesmendez.data.network

import android.content.Context
import com.example.kotlin.examenmovilesmendez.utilities.Constants.APPLICATION_ID
import com.example.kotlin.examenmovilesmendez.utilities.Constants.BASE_URL
import com.example.kotlin.examenmovilesmendez.utilities.Constants.CLIENT_KEY
import com.parse.Parse
import com.parse.ParseCloud
import com.parse.ParseException

// Objeto que encapsula la configuración de red y las llamadas a funciones en la nube
object NetworkModuleDI {

    /**
     * Inicializa el SDK de Parse con los valores definidos en Constants.
     * @param context Contexto de la aplicación o actual.
     */
    fun initializeParse(context: Context) {
        // Configuración del cliente Parse con la información de la aplicación
        val configuration = Parse.Configuration.Builder(context)
            .applicationId(APPLICATION_ID) // ID de la aplicación
            .clientKey(CLIENT_KEY) // Llave del cliente
            .server(BASE_URL) // URL del servidor Parse
            .build()

        // Inicialización del cliente Parse
        Parse.initialize(configuration)
    }

    /**
     * Realiza una llamada a una función en la nube en Parse.
     * @param T Tipo del resultado esperado.
     * @param functionName Nombre de la función en la nube.
     * @param params Parámetros requeridos por la función en la nube.
     * @param callback Función de callback que manejará el resultado o error.
     */
    fun <T> callCloudFunction(
        functionName: String,
        params: HashMap<String, Any>,
        callback: (T?, Exception?) -> Unit,
    ) {
        // Llama a la función encapsulada que maneja las llamadas a ParseCloud
        executeCloudFunction(functionName, params, callback)
    }

    /**
     * Encapsula la lógica para ejecutar funciones en la nube con ParseCloud.
     * @param T Tipo del resultado esperado.
     * @param name Nombre de la función en la nube.
     * @param parameters Parámetros requeridos por la función.
     * @param resultHandler Callback que procesa el resultado o error.
     */
    private fun <T> executeCloudFunction(
        name: String,
        parameters: HashMap<String, Any>,
        resultHandler: (T?, Exception?) -> Unit,
    ) {
        // Realiza la llamada en segundo plano y maneja el resultado
        ParseCloud.callFunctionInBackground(name, parameters) { result: T?, exception: ParseException? ->
            if (exception == null) {
                // Si no hay errores, se retorna el resultado
                resultHandler(result, null)
            } else {
                // Si ocurre un error, se envía la excepción al callback
                resultHandler(null, exception)
            }
        }
    }
}
