package jt.projects.employeestest.domain

data class ClientState(
    val clientModel: ClientModel? = null,
    val locationModel: LocationModel? = null,
    val error: String? = null,
    val isLoading: Boolean = true
)

