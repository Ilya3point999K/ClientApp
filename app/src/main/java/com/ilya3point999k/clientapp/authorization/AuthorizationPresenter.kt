package com.ilya3point999k.clientapp.authorization

import com.ilya3point999k.clientapp.IClientView

class AuthorizationPresenter(val clientView: IClientView) {
    fun editButtonClicked(){
        clientView.switchScreen(IClientView.SETTINGS_SCREEN, listOf())
    }

    fun loginButtonClicked(login: String, password: String){
        if (login == ""){
            clientView.pushError("Логин не может быть пустым")
            return
        }
        if (password == "") {
            clientView.pushError("Пароль не может быть пустым")
            return
        }

        clientView.switchScreen(IClientView.EDIT_SCREEN, listOf(login, password))
    }

    fun registerButtonClicked(login: String, password: String){
        if (login == ""){
            clientView.pushError("Введите желаемый логин")
            return
        }
        if (password == "") {
            clientView.pushError("Введите желаемый пароль")
            return
        }
        clientView.switchScreen(IClientView.REGISTRATION_SCREEN, listOf(login, password))
    }
}