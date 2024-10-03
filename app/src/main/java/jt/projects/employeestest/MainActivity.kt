package jt.projects.employeestest

import android.Manifest.permission.ACCESS_FINE_LOCATION
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
import jt.projects.employeestest.domain.ClientViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ClientViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ClientViewModel::class.java]
        observeViewModel()

        showCoordinates()
    }

    private fun observeViewModel() {
        viewModel.state.observe(this, Observer { state ->
            state.clientModel?.let { client ->
                with(binding.clientName) {
                    text = client.clientName
                    setTextColor(client.textColor)
                }
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

    private fun showCoordinates() {
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGmsAvailable()) {
                getGmsLocation()
            } else if (isHmsAvailable()) {
                getHmsLocation()
            } else {
                binding.location.text = "No location services available"
            }
        } else {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun getGmsLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location: Location? ->
            if (location != null) {
                updateLocationText(location)
            } else {
                binding.location.text = "Location not found"
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
                        updateLocationText(location)
                        fusedLocationClient.removeLocationUpdates(this)
                    } else {
                        binding.location.text = "Location not found"
                    }
                }
            },
            null
        )
    }

    private fun updateLocationText(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        binding.location.text = "Latitude: $latitude\nLongitude: $longitude"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCoordinates()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}