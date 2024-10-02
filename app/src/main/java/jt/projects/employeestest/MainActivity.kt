package jt.projects.employeestest

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

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

    fun isGmsAvailable(context: Context): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }

    fun isHmsAvailable(context: Context): Boolean {
        return HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(context) == ConnectionResult.SUCCESS
    }
}