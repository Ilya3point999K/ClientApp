package com.ilya3point999k.clientapp.registration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilya3point999k.clientapp.IClientView
import com.ilya3point999k.clientapp.MainActivity
import com.ilya3point999k.clientapp.R
import com.ilya3point999k.clientapp.databinding.FragmentRegistrationBinding


class RegistrationFragment(val destination: Int, val auth: List<String>): Fragment(), IClientView {

    private lateinit var bindings: FragmentRegistrationBinding
    private val presenter: RegistrationPresenter = RegistrationPresenter(this, destination, auth)
    private val IMAGE_REQUEST = 1229

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentRegistrationBinding.inflate(inflater, container, false)
        if(destination == IClientView.REGISTRATION_SCREEN){
            bindings.buttonProfilepic.setTag(0)
        }
        bindings.buttonProfilepic.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_REQUEST)
        }
        bindings.buttonCancel.setOnClickListener {
            presenter.cancelButtonClicked()
        }
        when(destination){
            IClientView.REGISTRATION_SCREEN -> {
                bindings.buttonAccept.text = getString(R.string.create)
                bindings.buttonAccept.setOnClickListener {
                    presenter.registerButtonClicked(bindings.buttonProfilepic, collectData())
                }
                presenter.checkCollision()
            }

            IClientView.EDIT_SCREEN -> {
                bindings.buttonAccept.text = getString(R.string.save)
                bindings.buttonAccept.setOnClickListener {
                    presenter.saveButtonClicked(bindings.buttonProfilepic, collectData())
                }
                val restoredData = presenter.restoreInfo()
                if (restoredData.isEmpty()) return bindings.root
                bindings.inputName.setText(restoredData.get("name"))
                bindings.inputSurname.setText(restoredData.get("surname"))
                bindings.inputPatronym.setText(restoredData.get("patronym"))
                if (restoredData.get("gender") == "male") bindings.radioMale.isChecked =
                    true else bindings.radioFemale.isChecked = true
                bindings.inputAge.setText(restoredData.get("age"))
                bindings.inputTown.setText(restoredData.get("town"))
                bindings.inputStreet.setText(restoredData.get("street"))
                bindings.inputBuilding.setText(restoredData.get("building"))
                bindings.inputFlat.setText(restoredData.get("flat"))
                bindings.inputOrganization.setText(restoredData.get("organization"))
                bindings.inputPost.setText(restoredData.get("post"))
                if (restoredData.get("photo") != "empty") {
                    //Конвертация изображения из строки обратно в Bitmap
                    val decodedString: ByteArray = Base64.decode(
                        restoredData.get("photo"),
                        Base64.DEFAULT
                    )
                    val decodedByte =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    bindings.buttonProfilepic.setImageBitmap(decodedByte)
                } else bindings.buttonProfilepic.setTag(0)
            }
        }
        return bindings.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data?.getData() != null) {
            if (requestCode == IMAGE_REQUEST) {
                val uri = data?.data;
                val imageStream = requireContext().getContentResolver().openInputStream(uri!!);
                val image: Bitmap = BitmapFactory.decodeStream(imageStream);
                bindings.buttonProfilepic.setImageBitmap(image)
                bindings.buttonProfilepic.setTag(1)
            }
        }
    }

    fun collectData() : HashMap<String, String>{
        val data: HashMap<String, String> = hashMapOf()
        data.put("login", auth[0])
        data.put("password", auth[1])
        data.put("name", bindings.inputName.text.toString())
        data.put("surname", bindings.inputSurname.text.toString())
        data.put("patronym", bindings.inputPatronym.text.toString())
        data.put("gender", if (bindings.radioMale.isChecked) "male" else "female")
        data.put("age", bindings.inputAge.text.toString())
        data.put("town", bindings.inputTown.text.toString())
        data.put("street", bindings.inputStreet.text.toString())
        data.put("building", bindings.inputBuilding.text.toString())
        data.put("flat", bindings.inputFlat.text.toString())
        data.put("organization", bindings.inputOrganization.text.toString())
        data.put("post", bindings.inputPost.text.toString())
        Log.w("Loggfrag", data.get("name")!!)
        return data
    }

    override fun pushError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun forceUpdate() {
        TODO("Not yet implemented")
    }

    override fun switchScreen(destination: Int, data: List<String>) {
        (this.requireContext() as MainActivity).switchScreen(destination, data)
    }

    override fun passContext(): Context {
        return requireContext()
    }
}