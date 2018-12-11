package app.felgueiras.musicapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.LastFMRESTClient
import app.felgueiras.musicapp.api.LastFMResponse
import app.felgueiras.musicapp.api.Tracks

import kotlinx.android.synthetic.main.activity_api.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // TODO: call API
        val API_BASE_URL = "http://ws.audioscrobbler.com/"
        val retrofit: Retrofit

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

        retrofit = builder
            .client(
                httpClient.build()
            )
            .build()

        val client = retrofit.create(LastFMRESTClient::class.java)

//        DoBackgroundNetTask(null).execute()

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

                track.forEach { t ->
                    Log.d("track", t.name +"-"+t.artist.name)
                }
//                val artist: Artist = resp!!.artist
//                Log.d("artist", artist.name)
//                artist.image.forEach { i ->
//                    Log.d("track", i.size + "-" + i.text)
//                }
            }


            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })
    }

}
