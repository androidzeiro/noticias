package br.com.raphael.noticias.androidtest.extensions

import br.com.raphael.noticias.extensions.fromJson
import com.google.gson.Gson

inline fun <reified T> Any.readJSON(fileName: String) =
    Gson().fromJson<T>(javaClass.classLoader!!.getResourceAsStream("json/${if (fileName.endsWith(".json")) fileName else "$fileName.json"}").bufferedReader().use { it.readText() })!!
