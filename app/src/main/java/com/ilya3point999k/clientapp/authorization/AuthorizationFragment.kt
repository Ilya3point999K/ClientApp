package com.ilya3point999k.clientapp.authorization

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ilya3point999k.clientapp.IClientView
import com.ilya3point999k.clientapp.MainActivity
import com.ilya3point999k.clientapp.R
import com.ilya3point999k.clientapp.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment(), IClientView {

    private lateinit var bindings: FragmentAuthorizationBinding
    private val presenter: AuthorizationPresenter = AuthorizationPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentAuthorizationBinding.inflate(inflater, container, false)

        bindings.buttonEdit.setOnClickListener {
            presenter.editButtonClicked()
        }
        bindings.buttonLogin.setOnClickListener {
            presenter.loginButtonClicked(bindings.inputLogin.text.toString(), bindings.inputPassword.text.toString())
        }
        bindings.buttonRegister.setOnClickListener {
            presenter.registerButtonClicked(bindings.inputLogin.text.toString(), bindings.inputPassword.text.toString())
        }

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