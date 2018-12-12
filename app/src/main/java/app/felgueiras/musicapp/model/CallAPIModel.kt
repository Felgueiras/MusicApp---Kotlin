package app.felgueiras.musicapp.model

import android.util.Log
import app.felgueiras.musicapp.api.LastFMRESTClient
import app.felgueiras.musicapp.api.LastFMResponse
import app.felgueiras.musicapp.api.Tracks
import app.felgueiras.musicapp.presenter.SongsListPresenter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO - Singleton
class CallAPIModel {

    val API_BASE_URL = "http://ws.audioscrobbler.com/"


    fun getSongsList(presenter: SongsListPresenter) {

        val retrofit: Retrofit

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

        retrofit = builder.client(httpClient.build()).build()

        val client = retrofit.create(LastFMRESTClient::class.java)

        // Fetch a list of the Github repositories.
        // TODO: parametrize calls
        val call = client.getSongsByCountry(country = "Portugal")
//        val call = client.getArtistInfo()
        Log.d("Call", call.request().url().toString())

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {
                // The network call was a success and we got a response

                val resp = response.body()

                // get reviews/books on this shelf
                val tracks: Tracks = resp!!.tracks
                val track = tracks.track

//                val artist: Artist = resp!!.artist
//                Log.d("artist", artist.name)
//                artist.image.forEach { i ->
//                    Log.d("track", i.size + "-" + i.text)
//                }

                // TODO - call presenter
                presenter.displaySongs(track)
            }


            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })


    }
}
