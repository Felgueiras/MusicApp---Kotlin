package app.felgueiras.musicapp.model

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
class Model(private val retrofit: Retrofit) : ModelContract {

    // create Retrofit client
    val client = retrofit.create(LastFMAPI::class.java)

    override fun getSongs(
        presenter: Any?, parameter: String,
        callback: ModelCallback<List<Track>>
    ) {

        // make API call
        val call: Call<LastFMResponse>? = client.getSongsByCountry(country = parameter)
        call!!.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {

                // network call was a success
                val resp = response.body()
                val tracks: Tracks = resp!!.tracks
                val track = tracks.tracks

                callback.onSuccess(track)
            }

            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                /**
                 * error making API call (most likely due to disabled internet connection),
                 * propagate error to Presenter
                 */
                callback.onError()
            }
        })


    }

    override fun getArtistDetail(
        presenter: Any?,
        parameter: String,
        callback: ModelCallback<Artist>
    ) {
        // make API call
        val call: Call<LastFMResponse>? = client.getArtistInfo(mbid = parameter)
        call!!.enqueue(object : Callback<LastFMResponse> {
            override fun onResponse(call: Call<LastFMResponse>, response: Response<LastFMResponse>) {

                // network call was a success
                val resp = response.body()
                val artist: Artist = resp!!.artist

                callback.onSuccess(artist)
            }

            override fun onFailure(call: Call<LastFMResponse>, t: Throwable) {
                /**
                 *error making API call (most likely due to disabled internet connection),
                 * propagate error to Presenter
                 */
                callback.onError()
            }
        })


    }

    companion object {
        fun getModel(): Model {
            val API_BASE_URL = "http://ws.audioscrobbler.com/"
            val httpClient = OkHttpClient.Builder()

            val builder = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
            val retrofit: Retrofit = builder.client(httpClient.build()).build()
            return Model(retrofit)
        }
    }
}
