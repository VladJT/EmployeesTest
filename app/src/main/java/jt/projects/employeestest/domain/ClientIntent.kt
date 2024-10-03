package jt.projects.employeestest.domain

sealed class ClientIntent {
    object LoadClientData : ClientIntent()
    object RequestLocation : ClientIntent()
}