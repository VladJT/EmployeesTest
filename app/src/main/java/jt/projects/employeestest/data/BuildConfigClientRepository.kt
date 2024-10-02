package jt.projects.employeestest.data

import android.graphics.Color
import jt.projects.employeestest.BuildConfig

class BuildConfigClientRepository : IClientRepository {

    override fun getClientName(): String {
        return BuildConfig.CLIENT_NAME
    }

    override fun getClientColor(): Int {
        val clientColorString = BuildConfig.CLIENT_COLOR
        return Color.parseColor("#$clientColorString")
    }

}