package app.felgueiras.musicapp.api

import retrofit2.Call
import retrofit2.http.*

/**
 * Methods to be called on the Last FM API.
 */
interface LastFMAPI {

    @GET("/2.0/")
    fun getSongsByCountry(
        @Query("method") method: String = "geo.gettoptracks",
        @Query("api_key") api_key: String = "ca2c5c573cd92f8df1a925be53f7c4c1",
        @Query("format") format: String = "json",
        @Query("country") country: String
    ): Call<LastFMResponse>

    @GET("/2.0/")
    fun getArtistInfo(
        @Query("method") method: String = "artist.getinfo",
        @Query("api_key") api_key: String = "ca2c5c573cd92f8df1a925be53f7c4c1",
        @Query("format") format: String = "json",
        @Query("mbid") mbid: String = "ada7a83c-e3e1-40f1-93f9-3e73dbc9298a"
    ): Call<LastFMResponse>

}
