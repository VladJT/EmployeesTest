package jt.projects.employeestest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jt.projects.employeestest.data.BuildConfigClientRepository
import jt.projects.employeestest.data.IClientRepository
import jt.projects.employeestest.domain.ClientIntent
import jt.projects.employeestest.domain.ClientModel
import jt.projects.employeestest.domain.ClientState
import jt.projects.employeestest.domain.LocationModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClientViewModel : ViewModel() {
    private val _state = MutableLiveData<ClientState>()
    val state: LiveData<ClientState> = _state
    private val clientRepo: IClientRepository = BuildConfigClientRepository()

    init {
        _state.value = ClientState(isLoading = true)
        viewModelScope.launch {
            delay(3000)
            handleIntent(ClientIntent.LoadClientData)
        }
    }

    fun handleIntent(intent: ClientIntent) {
        when (intent) {
            is ClientIntent.LoadClientData -> loadClientData()
            is ClientIntent.SetError -> setErrorMessage(intent.errorMessage)
            is ClientIntent.SetLocation -> setLocation(intent.lat, intent.lon)
        }
    }

    private fun setLocation(lat: Double, lon: Double) {
        val locationModel = LocationModel(lat, lon)
        _state.value = _state.value?.copy(locationModel = locationModel)
    }

    private fun setErrorMessage(errorMessage: String) {
        _state.value = ClientState(error = errorMessage)
    }

    private fun loadClientData() {
        val clientModel = ClientModel(clientRepo.getClientName(), clientRepo.getClientColor())
        _state.value = _state.value?.copy(clientModel = clientModel, isLoading = false)
    }


}