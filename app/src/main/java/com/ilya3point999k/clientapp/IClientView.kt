package com.ilya3point999k.clientapp

import android.content.Context

interface IClientView {
    fun pushError(message: String)
    fun forceUpdate()
    fun switchScreen(destination: Int, data: List<String>)
    fun passContext() : Context

    companion object{
        const val LOGIN_SCREEN = 0
        const val REGISTRATION_SCREEN = 1
        const val EDIT_SCREEN = 2
        const val SETTINGS_SCREEN = 3
    }
}