package com.espol.proyecto.asi_simulation.triage

import android.content.Context
import com.espol.proyecto.asi_simulation.triage.model.Canton
import com.espol.proyecto.asi_simulation.triage.model.Parish
import com.espol.proyecto.asi_simulation.triage.model.Province
//import org.dpppt.android.sdk.internal.logger.Logger
import org.json.JSONObject
import java.nio.charset.StandardCharsets

object LocationUtils{

    private val TAG: String = this::class.java.simpleName

    fun getLocationDataFromJSON(context : Context) : JSONObject {
        return getJSONFromAssets(context, "ecuador.json")
    }

    fun getProvinceList(context: Context) : List<Province>{
        val listaProvincias = mutableListOf<Province>()
        val jsonProvincias = getLocationDataFromJSON(context)


        jsonProvincias.keys().forEach { codeProvincia ->

            val jsonProvincia = jsonProvincias.optJSONObject(codeProvincia)!!
            if (!jsonProvincia.optString("provincia").equals("")){
                //Logger.d(TAG, codeProvincia.toString() + " -- " + jsonProvincia.optString("provincia").toString())

                val jsonCantones = jsonProvincia.optJSONObject("cantones")
                if (jsonCantones != null){
                    val listaCantones = mutableListOf<Canton>()
                    jsonCantones.keys().forEach {codeCanton ->
                        val jsonCanton = jsonCantones.optJSONObject(codeCanton)!!
                        if (!jsonCanton.optString("canton").equals("")){
                            //Logger.d(TAG, "\t" + codeCanton.toString() + " -- " + jsonCanton.optString("canton").toString())
                            val jsonParroquias = jsonCanton.optJSONObject("parroquias")
                            if (jsonParroquias != null){
                                val listaParroquias = mutableListOf<Parish>()
                                jsonParroquias.keys().forEach {codeParroquia ->
                                    val parroquiaNombre = jsonParroquias.optString(codeParroquia)
                                    if ( listaParroquias.find { it.name == parroquiaNombre } == null){
                                        listaParroquias.add(Parish(codeParroquia.toInt(), parroquiaNombre))
                                    }
                                    //Logger.d(TAG, "\t\t$codeParroquia -- $parroquiaNombre")
                                }
                                listaParroquias.sortBy { it.name }
                                listaParroquias.add(Parish(-1, "N/A"))
                                listaCantones.add(Canton(codeCanton.toInt(), jsonCanton.optString("canton"), listaParroquias))
                            }
                        }
                    }
                    listaCantones.sortBy { it.name }
                    listaCantones.add(Canton(-1, "N/A", mutableListOf<Parish>()))
                    listaProvincias.add(Province(codeProvincia.toInt(), jsonProvincia.optString("provincia"), listaCantones))
                }
            }
        }

        listaProvincias.sortBy { it.name }
        listaProvincias.add(Province(-1, "N/A", mutableListOf<Canton>()))

        return listaProvincias
    }

    fun getJSONFromAssets(context : Context, file_name: String?): JSONObject {
        var json_string = ""
        var json = JSONObject()
        try {
            val iStream = context.assets.open(file_name!!)
            val size = iStream.available()
            val buffer = ByteArray(size)
            iStream.read(buffer)
            iStream.close()
            json_string = String(buffer, StandardCharsets.UTF_8)
            json = JSONObject(json_string)
        } catch (e: Exception) {
            //Logger.e(TAG, "Error al leer .json desde Assets", e)
        }
        return json
    }
}