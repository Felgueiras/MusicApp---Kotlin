package app.felgueiras.musicapp.model

import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.*
import app.felgueiras.musicapp.contracts.ModelContract
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class responsible for making calls to the Last.fm API
 */
class Model : ModelContract.Model {


    private val API_BASE_URL = "http://ws.audioscrobbler.com/"
    val httpClient = OkHttpClient.Builder()

    val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
    val retrofit: Retrofit = builder.client(httpClient.build()).build()

    override fun getSongs(presenter: Any?, parameter: String, callType: String, callback: ModelCallback<List<Track>>) {

        val client = retrofit.create(LastFMAPI::class.java)

        var call: Call<LastFMResponse>? = null
        when (callType) {
            Constants.CALL_SONGS -> call = client.getSongsByCountry(country = parameter)
            Constants.CALL_ARTIST -> call = client.getArtistInfo(mbid = parameter)
        }

        // Execute the call asynchronously
        call!!.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {

                // The network call was a success and we got a response
                val resp = response.body()

                val tracks: Tracks = resp!!.tracks
                val track = tracks.tracks

                callback.onSuccess(track)
            }

            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })


    }

    override fun getArtistDetail(
        presenter: Any?,
        parameter: String,
        callType: String,
        callback: ModelCallback<Artist>
    ) {

        val client = retrofit.create(LastFMAPI::class.java)

        var call: Call<LastFMResponse>? = null
        when (callType) {
            Constants.CALL_SONGS -> call = client.getSongsByCountry(country = parameter)
            Constants.CALL_ARTIST -> call = client.getArtistInfo(mbid = parameter)
        }

        // Execute the call asynchronously
        call!!.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {

                // The network call was a success and we got a response
                val resp = response.body()

                val artist: Artist = resp!!.artist

                callback.onSuccess(artist)
            }

            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })


    }
}
