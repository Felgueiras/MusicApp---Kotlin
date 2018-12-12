package app.felgueiras.musicapp

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import java.io.IOException
import java.util.*

class FetchAddressIntentService : IntentService("FetchAddressIntentService") {

    private var receiver: ResultReceiver? = null

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message) }
        receiver?.send(resultCode, bundle)
    }

    override fun onHandleIntent(intent: Intent?) {
        intent ?: return

        var errorMessage = ""

        // Get the location passed to this service through an extra.
        val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRA)
        receiver = intent.getParcelableExtra<MainActivity.AddressResultReceiver>(Constants.RECEIVER)


        val geocoder = Geocoder(this, Locale.getDefault())

        var addresses: List<Address> = emptyList()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                // In this sample, we get just a single address.
                1
            )
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = "service not available"
            Log.e(Constants.TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "invalid lat long"
            Log.e(
                Constants.TAG, "$errorMessage. Latitude = $location.latitude , " +
                        "Longitude =  $location.longitude", illegalArgumentException
            )
        }

        // Handle case where no address was found.
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = "no address found"
                Log.e(Constants.TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            val country: String = addresses[0].countryName
            deliverResultToReceiver(
                Constants.SUCCESS_RESULT,
                country
            )
        }
    }


}
