package app.felgueiras.musicapp.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.FetchAddressIntentService
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SplashScreenContract
import app.felgueiras.musicapp.model.ModelCallback


/**
 * Handle Splash Screen.
 */
class SplashScreenPresenter(
    val model: ModelContract
) : BasePresenter<SplashScreenContract.View>(), SplashScreenContract.Presenter {


    /**
     * LocationManager: get location updates.
     */
    private lateinit var locationManager: LocationManager
    /**
     * Handle addresses.
     */
    private lateinit var resultReceiver: AddressResultReceiver

    /**
     * User's country (from current location)
     */
    lateinit var countryName: String


    override fun requestLocationPermission() {
        // responsible for handling addresses
        resultReceiver = AddressResultReceiver(Handler())
        // check location permission
        if (view?.getViewActivity()!!.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // not granted, request
            view?.getViewActivity()!!.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_PERMISSION
            )
        } else {
            // already granted, listen for location changes
            onLocationPermissionsResult()
        }
    }

    // listen to changes in location
    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            startIntentService(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    /**
     * Fetch address using a service
     */
    override fun startIntentService(lastLocation: Location) {

        Log.d(Constants.TAG, "starting intent")
        val intent = Intent(view!!.getViewActivity(), FetchAddressIntentService::class.java).apply {
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
            putExtra(Constants.RECEIVER, resultReceiver)
        }
        (view as Activity).startService(intent)
    }

    override fun onLocationPermissionsResult() {
        // show loading progress bar
        view!!.waitingLocation()

        Handler().postDelayed({
            // create LocationManager
            locationManager =
                    view!!.getViewActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager;
            try {

                // Request current location (network)
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                );
            } catch (ex: SecurityException) {
            }

            // check if location/network enabled
            var gps_enabled = false
            var network_enabled = false

            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ex: Exception) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                view?.showLocationDisabledError();
            }


        }, 1500L)

    }


    /**
     * Get songs list from Model
     */
    fun getSongsList(country: String) {
        model.getSongs(this as Any, country, object : ModelCallback<List<Track>> {
            override fun onSuccess(tracks: List<Track>?) {
                // navigate to songs list
                view?.goToSongsList(ArrayList(tracks), countryName)
            }

            override fun onError() {
                // show internet connection disabled error
                view?.showNetworkError()
            }
        })
    }


    /**
     * Handle addresses.
     */
    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            val countryInfo = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""

            // successful request
            if (resultCode == Constants.SUCCESS_RESULT) {
                // cancel location listener
                locationManager.removeUpdates(locationListener)
                // display country info
                countryName = countryInfo.split("-")[0]
                val countryCode = countryInfo.split("-")[1]
                val countryFlagURL = "https://www.countryflags.io/${countryCode}/flat/64.png"
                view!!.showCountryInfo(countryFlagURL, "Loading top songs for ${countryName}");

                // load songs
                getSongsList(countryName)
            }
        }
    }
}
