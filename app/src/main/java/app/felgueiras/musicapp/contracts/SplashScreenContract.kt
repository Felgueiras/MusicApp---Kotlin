package app.felgueiras.musicapp.contracts

import android.location.Location
import app.felgueiras.musicapp.api.Track

interface SplashScreenContract {

    /**
     * Operations offered from View to Presenter
     */
    interface View {

        fun goToSongsList(tracks: ArrayList<Track>, countryName: String)

        fun waitingLocation()
        fun showCountryInfo(countryFlagURL: String, countryName: String)

    }

    /**
     * Operations offered from Presenter to View
     */
    interface Presenter {

        fun startIntentService(lastLocation: Location)

        fun listenLocationUpdates()

        fun getLocationInfo()
    }
}
