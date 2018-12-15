package app.felgueiras.musicapp

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SongDetailContract
import app.felgueiras.musicapp.model.ModelCallback
import app.felgueiras.musicapp.presenter.SongDetailPresenter
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class DetailScreenTests {

    /**
     * "real" presenter
     */
    private lateinit var presenter: SongDetailPresenter
    /**
     * mocked view/activity
     */
    private lateinit var view: SongDetailContract.View
    /**
     * mocked model
     */
    private lateinit var model: ModelContract

    private val mbid = "mbid"

    private val artist = Artist()


    @Before
    fun setup() {
        model = mock()
        presenter = SongDetailPresenter(model)
        view = mock()
        presenter.attachView(view)
    }


    @Test
    fun getSongsList_callsMakeAPICall() {

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<Artist> = it.getArgument(2)
            callback.onSuccess(artist)
        }.whenever(model).getArtistDetail(any(), eq(mbid),  any())

        presenter.getArtistDetail(mbid)

        verify(model).getArtistDetail(any(), eq(mbid),  any())

        verify(view).displayArtistInfo(eq(artist))
    }
}