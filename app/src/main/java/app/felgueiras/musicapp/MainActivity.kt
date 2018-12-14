package app.felgueiras.musicapp

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongsListContract
import app.felgueiras.musicapp.presenter.SongsListPresenter
import app.felgueiras.musicapp.view.SongsListActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

/**
 * App's launcher activity, requests location access permission,
 * gets country, displays its flag and gets top songs.
 */
class MainActivity : AppCompatActivity(), SongsListContract.View {


    private lateinit var locationManager: LocationManager
    private var lastLocation: Location? = null
    private lateinit var resultReceiver: AddressResultReceiver
    private lateinit var context: Context

    private lateinit var presenter: SongsListContract.Presenter


    private val TIME_OUT = 1500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        // request location permissions
        resultReceiver = AddressResultReceiver(Handler())
        checkLocationPermission()
    }


    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
//            Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            Constants.REQUEST_PERMISSION
                        )
                    })
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.REQUEST_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {


                        listenLocationUpdates();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return
            }
        }
    }

    private fun listenLocationUpdates() {

        Handler().postDelayed({
            progressBar.visibility = View.VISIBLE

            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;
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
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage(getString(R.string.location_disabled))
                dialog.setPositiveButton(
                    getString(R.string.open_location_settings)
                ) { _, _ ->
                    //                    val myIntent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
//                    startActivity(myIntent)
                }
                dialog.setNegativeButton(
                    getString(R.string.close)
                ) { _, _ ->
                }
                dialog.show()
            }


        }, TIME_OUT.toLong())


    }

    override fun displaySongs(track: MutableList<Track>) {
        // start other activity
        val intent = Intent(context, SongsListActivity::class.java)
        val a = ArrayList(track)
        intent.putExtra(Constants.TRACKS, a)
        intent.putExtra(Constants.COUNTRY, countryName)
        context.startActivity(intent)
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


    private lateinit var countryName: String
    private lateinit var countryCode: String

    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            val countryInfo = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""

            if (resultCode == Constants.SUCCESS_RESULT) {
                // cancel location listener
                locationManager.removeUpdates(locationListener)
                // display country info
                countryName = countryInfo.split("-")[0]
                countryCode = countryInfo.split("-")[1]
                // country flag URL
                val countryFlagURL = "https://www.countryflags.io/${countryCode}/flat/64.png"
                Glide.with(context).load(countryFlagURL).into(countryFlag);
                info.text = "Loading top songs for ${countryName}"
                countryFlag.visibility = View.VISIBLE
                info.visibility = View.VISIBLE

                viewAppear(countryFlag as View)

                // load songs
                presenter = SongsListPresenter(context as SongsListContract.View)
                presenter.getSongsList(countryName)
            }
        }
    }

    private var mShortAnimationDuration: Int = 1000

    private fun viewAppear(view: View) {
        view.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration.toLong())
                .setListener(null)
        }

    }


}
