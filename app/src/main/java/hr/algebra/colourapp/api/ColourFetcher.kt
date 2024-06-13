package hr.algebra.colourapp.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.colourapp.COLOUR_PROVIDER_CONTENT_URI
import hr.algebra.colourapp.ColourReceiver
import hr.algebra.colourapp.framework.sendBroadcast
import hr.algebra.colourapp.handler.downloadImageAndStore
import hr.algebra.colourapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ColourFetcher(private val context: Context) {

    private val colourApi: ColourApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        colourApi = retrofit.create(ColourApi::class.java)
    }

    fun fetchItems(format: String) {

        val request = colourApi.fetchItems(format = "json")

        request.enqueue(object : Callback<List<ColourItem>> {
            override fun onResponse(
                call: Call<List<ColourItem>>,
                response: Response<List<ColourItem>>
            ) {
                response.body()?.let { populateItems(it) }
            }

            override fun onFailure(call: Call<List<ColourItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }

        })

    }

    private fun populateItems(colourItems: List<ColourItem>) {
        //foreground
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            //background
            colourItems.forEach {
                val picturePath = downloadImageAndStore(context, it.imageUrl)

                val values = ContentValues().apply {
                    put(Item::title.name, it.title)
                    put(Item::username.name, it.userName)
                    put(Item::picturePath.name, picturePath?:"")
                    put(Item::hexadecimal.name, it.hex)
                    put(Item::read.name, false)
                }

                context.contentResolver.insert(COLOUR_PROVIDER_CONTENT_URI, values)
            }

            context.sendBroadcast<ColourReceiver>()
        }



    }
}