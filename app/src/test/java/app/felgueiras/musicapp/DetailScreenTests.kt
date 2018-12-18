package app.felgueiras.musicapp

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
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

    val track = Track()

    val songName = "song songName"

    @Before
    fun setup() {
        model = mock()
        presenter = SongDetailPresenter(model)
        view = mock()
        presenter.attachView(view)

        setupArtist();
        setupTrack()

        presenter.track = track
        presenter.artist = artist
    }

    private fun setupArtist() {
        artist.mbid = mbid
        artist.name = "artist name"
        artist.bio = Artist.Bio()
        artist.bio.summary = "123"
    }

    private fun setupTrack(){
        track.artist = artist
        track.name  = songName
    }


    @Test
    fun getArtistDetail_callsdisplayArtistInfo() {

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<Artist> = it.getArgument(2)
            callback.onSuccess(artist)
        }.whenever(model).getArtistDetail(any(), eq(mbid), any())

        presenter.getArtistDetail(track)

        verify(model).getArtistDetail(any(), eq(mbid), any())

        verify(view).displayArtistInfo(eq(artist))
    }

    @Test
    fun withoutNetwork_getArtistDetail_callsShowNetworkError() {

        // mock makeAPICall method
        doAnswer {
            val callback: ModelCallback<Artist> = it.getArgument(2)
            callback.onError()
        }.whenever(model).getArtistDetail(any(), eq(mbid), any())


        presenter.getArtistDetail(track)

        verify(model).getArtistDetail(any(), eq(mbid), any())

        verify(view).showNetworkError()

        verify(view, never()).displayArtistInfo(any())
    }

    @Test
    fun bioTextClicked_callsDisplayFullBio() {

        presenter.bioTextClicked()

        verify(view).displayFullBio()
    }

    @Test
    fun handleShare_callsShareData() {

        presenter.handleShare()

        verify(view).shareData(any())
    }
}