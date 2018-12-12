package app.felgueiras.musicapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.support.v7.app.AlertDialog
import app.felgueiras.musicapp.view.SongsListActivity


class MainActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private var lastLocation: Location? = null
    private lateinit var resultReceiver: AddressResultReceiver
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        // initiate receiver
        resultReceiver = AddressResultReceiver(Handler())


        // request location permissions
        // TODO - check permission granted
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)


        // check if location/network enabled
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(getString(R.string.location_disabled))
            dialog.setPositiveButton(
                getString(R.string.open_location_settings)
            ) { _, _ ->
                val myIntent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            dialog.setNegativeButton(
                getString(R.string.close)
            ) { _, _ ->
            }
            dialog.show()
        }

        try {
            // Request current location
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

    }

    // listen to changes in location
    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            lastLocation = location
            startIntentService()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    /**
     * Fetch address using a service
     */
    private fun startIntentService() {

        Log.d(Constants.TAG, "starting intent")
        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
            putExtra(Constants.RECEIVER, resultReceiver)
        }
        startService(intent)
    }


    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            val country = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""

            if (resultCode == Constants.SUCCESS_RESULT) {
                // cancel location listener
                locationManager.removeUpdates(locationListener)
                val intent = Intent(context, SongsListActivity::class.java)
                intent.putExtra(Constants.COUNTRY, country)
                context.startActivity(intent)
            }
        }
    }

}
