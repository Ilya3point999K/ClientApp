package com.ilya3point999k.clientapp.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ilya3point999k.clientapp.IClientView
import com.ilya3point999k.clientapp.MainActivity
import com.ilya3point999k.clientapp.R
import com.ilya3point999k.clientapp.databinding.FragmentSettingsBinding


class SettingsFragment(val preferences: SharedPreferences) : Fragment(), IClientView {

    private lateinit var bindings: FragmentSettingsBinding
    private val presenter = SettingsPresenter(this, preferences)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentSettingsBinding.inflate(inflater, container, false)
        bindings.buttonAcceptSettings.setOnClickListener {
            presenter.acceptButtonClicked(listOf(bindings.inputIp.text.toString(), bindings.inputPort.text.toString()))
        }
        bindings.buttonCancelSettings.setOnClickListener {
            presenter.cancelButtonClicked()
        }
        val settings = presenter.restoreSettings()
        bindings.inputIp.setText(settings[0])
        bindings.inputPort.setText(settings[1])
        return bindings.root
    }

    override fun pushError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun forceUpdate() {
    }

    override fun switchScreen(destination: Int, data: List<String>) {
        (this.requireContext() as MainActivity).switchScreen(destination, data)
    }

    override fun passContext(): Context {
        return requireContext()
    }


}