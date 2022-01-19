package com.ilya3point999k.clientapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilya3point999k.clientapp.authorization.AuthorizationFragment
import com.ilya3point999k.clientapp.databinding.ActivityMainBinding
import com.ilya3point999k.clientapp.registration.RegistrationFragment
import com.ilya3point999k.clientapp.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    lateinit var bindings: ActivityMainBinding
    val authorizationFragment = AuthorizationFragment()
    //Ленивая инициализация, иначе Context не успевает создаться
    val settingsFragment by lazy { SettingsFragment(getPreferences(Context.MODE_PRIVATE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        supportFragmentManager.beginTransaction().add(R.id.main, authorizationFragment).addToBackStack("Login").commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count > 1) {
            supportFragmentManager.popBackStack()
        }
        if(supportFragmentManager.getBackStackEntryAt(count - 1).name == "Login"){
            super.onBackPressed()
            finishAndRemoveTask()
        }
    }

    fun switchScreen(destination: Int, data: List<String>){
        when (destination){
            IClientView.LOGIN_SCREEN -> {
                supportFragmentManager.beginTransaction().replace(R.id.main, authorizationFragment).addToBackStack("Login").commit()
            }
            IClientView.SETTINGS_SCREEN -> {
                supportFragmentManager.beginTransaction().replace(R.id.main, settingsFragment).addToBackStack("Settings").commit()
            }
            IClientView.REGISTRATION_SCREEN -> {
                val registrationFragment = RegistrationFragment(destination, data)
                supportFragmentManager.beginTransaction().replace(R.id.main, registrationFragment).addToBackStack("Registration").commit()
            }
            IClientView.EDIT_SCREEN -> {
                val registrationFragment = RegistrationFragment(destination, data)
                supportFragmentManager.beginTransaction().replace(R.id.main, registrationFragment).addToBackStack("Edit").commit()
            }
        }
    }

    fun getSettings(): List<String>{
        return listOf(getPreferences(Context.MODE_PRIVATE).getString("ip", "127.0.0.1")!!, getPreferences(Context.MODE_PRIVATE).getString("port", "1111")!!)
    }
}