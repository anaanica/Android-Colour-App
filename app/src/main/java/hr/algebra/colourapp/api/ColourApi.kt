package hr.algebra.colourapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://www.colourlovers.com/api/"

interface ColourApi {
    @GET("colors/new")
    fun fetchItems(@Query("format") format: String = "json")
            : Call<List<ColourItem>>
}