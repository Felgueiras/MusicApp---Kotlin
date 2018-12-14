package app.felgueiras.musicapp.model

import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.LastFMAPI
import app.felgueiras.musicapp.api.LastFMResponse
import app.felgueiras.musicapp.api.Tracks
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.presenter.SongsListPresenter
import app.felgueiras.musicapp.presenter.SongDetailPresenter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton class responsible making calls to the Last.fm API
 */
object CallAPIModel : ModelContract.Model {


    private const val API_BASE_URL = "http://ws.audioscrobbler.com/"

    override fun makeAPICall(presenter: Object?, parameter: String, callType: String) {

        val retrofit: Retrofit

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )



        retrofit = builder.client(httpClient.build()).build()

        val client = retrofit.create(LastFMAPI::class.java)

        var call: Call<LastFMResponse>? = null
        when (callType) {
            Constants.CALL_SONGS -> call = client.getSongsByCountry(country = parameter)
            Constants.CALL_ARTIST -> call = client.getArtistInfo(mbid = parameter)
        }

        Log.d("Call", call!!.request().url().toString())

        // Execute the call asynchronously
        call.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {

                // The network call was a success and we got a response
                val resp = response.body()

                when (callType) {
                    Constants.CALL_SONGS -> {
                        val tracks: Tracks = resp!!.tracks
                        val track = tracks.tracks

                        val pres = presenter as SongsListPresenter
                        pres.displaySongs(track)
                    }
                    Constants.CALL_ARTIST -> {
                        val artist: Artist = resp!!.artist

                        val pres = presenter as SongDetailPresenter
                        pres.displayArtistDetails(artist)
                    }
                }

            }


            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })


    }
}
