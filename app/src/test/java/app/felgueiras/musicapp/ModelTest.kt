package app.felgueiras.musicapp

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.model.Model
import app.felgueiras.musicapp.model.ModelCallback
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * API model test
 */
class ModelTest {

    lateinit var model: ModelContract

    lateinit var mockServer: MockWebServer


    @Before
    fun setUp() {

        // Initialize mock webserver
        mockServer = MockWebServer()
        // Start the local server
        mockServer.start()


        // Get an okhttp client
        val okHttpClient = OkHttpClient.Builder()
            .build()

        // Get an instance of Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.sample.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        model = Model(retrofit)
    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        val json = String(file.readBytes())
        return json
    }


    @Test
    fun testBlogsReturnsListOfBlogs() {

        val path = "/path"

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/artist.json"))
        // Enqueue request
        mockServer.enqueue(mockResponse)


        val callback: ModelCallback<Artist> = mock();
        // Call the API
        model.getArtistDetail(any(), "mbid", callback)

        // Get the request that was just made
        val request = mockServer.takeRequest()
        // Make sure we made the request to the required path
        assertEquals(path, request.path)

    }


}
