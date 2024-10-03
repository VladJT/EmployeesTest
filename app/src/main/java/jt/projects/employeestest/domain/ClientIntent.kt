package jt.projects.employeestest.domain

sealed class ClientIntent {
    data object LoadClientData : ClientIntent()
    data class SetLocation(val lat: Double, val lon: Double) : ClientIntent()
    data class SetError(val errorMessage: String) : ClientIntent()
}