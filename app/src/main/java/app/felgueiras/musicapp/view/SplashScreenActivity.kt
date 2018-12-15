package app.felgueiras.musicapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SplashScreenContract
import app.felgueiras.musicapp.model.Model
import app.felgueiras.musicapp.presenter.SplashScreenPresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

/**
 * App's launcher activity / splash screen, displays loading indicator and country info.
 */
class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    private val presenter: SplashScreenPresenter = SplashScreenPresenter(Model())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // attach view to presenter
        presenter.attachView(this)

        // request location permissions
        presenter.getLocationInfo()
    }

    /**
     * Display country info (name, flag)
     */
    override fun showCountryInfo(countryFlagURL: String, infoString: String) {
        Glide.with(this).load(countryFlagURL).into(countryFlag);
        info.text = infoString
        countryFlag.visibility = View.VISIBLE
        info.visibility = View.VISIBLE
        countryFlag.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity
            animate()
                .alpha(1f)
                .setDuration(1000L)
        }
    }

    /**
     * Wait for location info.
     */
    override fun waitingLocation() {
        progressBar.visibility = View.VISIBLE
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

                        presenter.listenLocationUpdates();
                    }

                } else {

                    // TODO -  permission denied, close the app ?
                }
                return
            }
        }
    }


    /**
     * Navigate to songs list.
     */
    override fun goToSongsList(tracks: ArrayList<Track>, countryName: String) {
        val intent = Intent(this, SongsListActivity::class.java)
        intent.putExtra(Constants.TRACKS, tracks)
        intent.putExtra(Constants.COUNTRY, countryName)
        startActivity(intent)
    }


    /**
     * Detach view from presenter.
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }


}
