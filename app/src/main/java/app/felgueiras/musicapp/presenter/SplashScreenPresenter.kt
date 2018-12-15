package app.felgueiras.musicapp.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.FetchAddressIntentService
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SplashScreenContract
import app.felgueiras.musicapp.model.ModelCallback

class SplashScreenPresenter(
    val model: ModelContract.Model
) : BasePresenter<SplashScreenContract.View>(), SplashScreenContract.Presenter {

    /**
     * LocationManager: get location updates.
     */
    private lateinit var locationManager: LocationManager
    /**
     * Handle addresses.
     */
    private lateinit var resultReceiver: AddressResultReceiver


    fun getLocationInfo(): Boolean {
        resultReceiver = AddressResultReceiver(Handler())

        if (ContextCompat.checkSelfPermission(
                view as Context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
//            Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    view as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(view as Context)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                        //Prompt the user once explanation has been shown
//                        ActivityCompat.requestPermissions(
//                            this@SplashScreenActivity,
//                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                            Constants.REQUEST_PERMISSION
//                        )
                    })
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    view as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.REQUEST_PERMISSION
                )
            }
            return false
        } else {
            listenLocationUpdates()
            return true
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
        val intent = Intent(view as Context, FetchAddressIntentService::class.java).apply {
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
            putExtra(Constants.RECEIVER, resultReceiver)
        }
        (view as Activity).startService(intent)
    }

    override fun listenLocationUpdates() {

        view!!.waitingLocation()

        Handler().postDelayed({

            locationManager =
                    (view as Activity).getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager;
            try {
                // Request current location
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                );
            } catch (ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available");
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
                val dialog = AlertDialog.Builder(view as Activity)
                dialog.setMessage((view as Activity).getString(R.string.location_disabled))
                dialog.setPositiveButton(
                    (view as Activity).getString(R.string.open_location_settings)
                ) { _, _ ->
                    //                    val myIntent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
//                    startActivity(myIntent)
                }
                dialog.setNegativeButton(
                    (view as Activity).getString(R.string.close)
                ) { _, _ ->
                }
                dialog.show()
            }


        }, 1500L)


    }


    /**
     * Get songs list from Model
     */
    fun getSongsList(country: String) {
        model.getSongs(this as Object, country, Constants.CALL_SONGS, object : ModelCallback<List<Track>> {
            // 3
            override fun onSuccess(tracks: List<Track>?) {

                view!!.goToSongsList(ArrayList(tracks), countryName)
            }

            // 4
            override fun onError() {
                // TODO - show error
//                view?.showError()
            }
        })
    }

    lateinit var countryName: String


    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            val countryInfo = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""

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
