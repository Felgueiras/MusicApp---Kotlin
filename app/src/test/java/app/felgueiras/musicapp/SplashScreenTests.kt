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
    private lateinit var model: ModelContract

    private val countryName = "Portugal"

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

        presenter.countryName = countryName

    }


    @Test
    fun getSongsList_callsGoToSongList() {

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<List<Track>> = it.getArgument(2)
            callback.onSuccess(list)
        }.whenever(model).getSongs(any(), eq(countryName), any())

        presenter.getSongsList(countryName)

        verify(model).getSongs(any(), eq(countryName), any())

        verify(view).goToSongsList(any(), any())

        verify(view, never()).showNetworkError()
    }

    @Test
    fun withoutNetwork_getSongsList_callsShowLocationError() {

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<List<Track>> = it.getArgument(2)
            callback.onError()
        }.whenever(model).getSongs(any(), eq(countryName), any())

        presenter.getSongsList(countryName)

        verify(model).getSongs(any(), eq(countryName), any())

        verify(view).showNetworkError()

        verify(view, never()).goToSongsList(any(), any())
    }
}