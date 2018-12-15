package app.felgueiras.musicapp

import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SplashScreenContract
import app.felgueiras.musicapp.model.ModelCallback
import app.felgueiras.musicapp.presenter.SplashScreenPresenter
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class SplashScreenTests {

    /**
     * "real" presenter
     */
    private lateinit var presenter: SplashScreenPresenter
    /**
     * mocked view/activity
     */
    private lateinit var view: SplashScreenContract.View
    /**
     * mocked model
     */
    private lateinit var model: ModelContract.Model

    val countryName = "Portugal"

    val track = Track()
    lateinit var list: MutableList<Track>

    @Before
    fun setup() {
        model = mock()
        presenter = SplashScreenPresenter(model)
        view = mock()
        presenter.attachView(view)

        track.name = "song #2"
        list = mutableListOf(track)

    }


    @Test
    fun getSongsList_callsMakeAPICall() {

        presenter.getSongsList(countryName)

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<List<Track>> = it.getArgument(1)
            callback.onSuccess(list)
        }.whenever(model).getSongs(any(), eq(countryName), eq(Constants.CALL_SONGS), any())

        verify(model).getSongs(any(), eq(countryName), eq(Constants.CALL_SONGS), any())


//        verify(view).goToSongsList(any(), any())
    }
}