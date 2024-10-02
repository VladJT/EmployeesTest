package jt.projects.employeestest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability
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

        checkServices()
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

    private fun checkServices() {
        println(isGmsAvailable().toString())
        println(isHmsAvailable().toString())
    }


    private fun isGmsAvailable(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(applicationContext) == com.google.android.gms.common.ConnectionResult.SUCCESS
    }

    private fun isHmsAvailable(): Boolean {
        return HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(applicationContext) == com.huawei.hms.api.ConnectionResult.SUCCESS
    }
}