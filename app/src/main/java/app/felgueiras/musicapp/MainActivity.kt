package app.felgueiras.musicapp

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
import android.widget.Button

class MainActivity : AppCompatActivity(){

    private var locationManager: LocationManager? = null
    private var lastLocation: Location? = null

    private lateinit var resultReceiver: AddressResultReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)


        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?;

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener { view ->
            startIntentService()
        }

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

    }

    private fun startIntentService() {

        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
            // putExtra(Constants.RECEIVER, resultReceiver)
        }
        Log.d("myTag", "starting intent")
        startService(intent)
    }

    private val locationListener: LocationListener = object : LocationListener {
        // val locationInfo = findViewById<TextView>(R.id.location_info)

        override fun onLocationChanged(location: Location) {
            // locationInfo.setText("" + location.longitude + ":" + location.latitude);
            Log.d("myTag", "" + location.longitude + ":" + location.latitude)
            lastLocation = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            // Display the address string
            // or an error message sent from the intent service.
            val addressOutput = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""
            Log.d("myTag",addressOutput);
            //displayAddressOutput()

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found))
            }

        }
    }

}
