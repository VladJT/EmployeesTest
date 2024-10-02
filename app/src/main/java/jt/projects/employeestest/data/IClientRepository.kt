package jt.projects.employeestest.data

interface IClientRepository {
    fun getClientName(): String
    fun getClientColor(): Int
}