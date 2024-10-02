package jt.projects.employeestest.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.employeestest.data.BuildConfigClientRepository
import jt.projects.employeestest.data.IClientRepository

class ClientViewModel : ViewModel() {
    private val _state = MutableLiveData<ClientState>()
    val state: LiveData<ClientState> = _state
    private val clientRepo: IClientRepository = BuildConfigClientRepository()

    init {
        handleIntent(ClientIntent.LoadClientData)
    }

    private fun handleIntent(intent: ClientIntent) {
        when (intent) {
            is ClientIntent.LoadClientData -> loadClientData()
        }
    }

    private fun loadClientData() {

        val clientModel =
            ClientModel(clientRepo.getClientName(), clientRepo.getClientColor())
        _state.value = ClientState(clientModel, isLoading = false)
    }
}