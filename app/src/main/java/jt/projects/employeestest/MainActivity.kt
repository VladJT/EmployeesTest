package jt.projects.employeestest

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.huawei.hms.api.HuaweiApiAvailability
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationResult
import jt.projects.employeestest.databinding.ActivityMainBinding
import jt.projects.employeestest.domain.ClientIntent
import jt.projects.employeestest.ui.ClientViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ClientViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ClientViewModel::class.java]
        observeViewModel()

        requestCurrentLocation()
    }

    private fun observeViewModel() {
        viewModel.state.observe(this, Observer { state ->
            state.clientModel?.let { client ->
                binding.clientName.text = client.clientName
                binding.clientName.setTextColor(client.textColor)
            }

            state.locationModel?.let { location ->
                binding.location.text =
                    "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"
            }

            state.error?.let {
                binding.location.text = it
            }

            if (state.isLoading) {
                binding.location.text = "Loading..."
            }
        })
    }

    private fun isGmsAvailable(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(applicationContext) == com.google.android.gms.common.ConnectionResult.SUCCESS
    }

    private fun isHmsAvailable(): Boolean {
        return HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(applicationContext) == com.huawei.hms.api.ConnectionResult.SUCCESS
    }

    private fun requestCurrentLocation() {
        when {
            checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED -> {
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }

            isGmsAvailable() -> {
                getGmsLocation()
            }

            isHmsAvailable() -> {
                getHmsLocation()
            }

            else -> {
                viewModel.handleIntent(ClientIntent.SetError(getString(R.string.error_location_not_found)))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getGmsLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location: Location? ->
            if (location != null) {
                viewModel.handleIntent(
                    ClientIntent.SetLocation(
                        location.latitude,
                        location.longitude
                    )
                )
            } else {
                viewModel.handleIntent(ClientIntent.SetError(getString(R.string.error_location_not_found)))
            }
        }
    }

    private fun getHmsLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Интервал обновления местоположения в миллисекундах
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val fusedLocationClient = com.huawei.hms.location.FusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : com.huawei.hms.location.LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        viewModel.handleIntent(
                            ClientIntent.SetLocation(
                                location.latitude,
                                location.longitude
                            )
                        )
                        fusedLocationClient.removeLocationUpdates(this)
                    } else {
                        viewModel.handleIntent(ClientIntent.SetError(getString(R.string.error_location_not_found)))
                    }
                }
            },
            null
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestCurrentLocation()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}