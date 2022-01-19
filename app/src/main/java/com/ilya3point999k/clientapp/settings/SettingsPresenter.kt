package com.ilya3point999k.clientapp.settings

import android.content.SharedPreferences
import com.ilya3point999k.clientapp.IClientView

class SettingsPresenter(val clientView: IClientView, val preferences: SharedPreferences) {
    fun acceptButtonClicked(data: List<String>){
        preferences.edit().putString("ip", data[0]).putString("port", data[1]).apply()
        clientView.pushError("Сохранено")
    }

    fun cancelButtonClicked(){
        clientView.switchScreen(IClientView.LOGIN_SCREEN, listOf())
    }

    fun restoreSettings(): List<String>{
        return listOf(preferences.getString("ip", "127.0.0.1")!!, preferences.getString("port", "1111")!!)
    }
}