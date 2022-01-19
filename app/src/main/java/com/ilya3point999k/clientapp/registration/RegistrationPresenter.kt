package com.ilya3point999k.clientapp.registration

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.ilya3point999k.clientapp.IClientModel
import com.ilya3point999k.clientapp.IClientView
import com.ilya3point999k.clientapp.LocalDatabase
import com.ilya3point999k.clientapp.MainActivity
import java.io.ByteArrayOutputStream


class RegistrationPresenter(val clientView: IClientView, val destination: Int, val auth: List<String>) {
    //Ленивая инициализация, иначе Context не успевает создаться
    val model: IClientModel by lazy{ LocalDatabase(clientView.passContext()) }
    lateinit var id: String

    fun restoreInfo(): HashMap<String, String>{
        val respond = model.get(auth)
        if (respond.isEmpty()){
            clientView.pushError("Неверный логин или пароль")
            clientView.switchScreen(IClientView.LOGIN_SCREEN, listOf())
        }
        else id = respond.get("id")!!
        return respond
    }

    fun checkCollision(){
        val respond = model.get(auth)
        if (!respond.isEmpty()){
            clientView.pushError("Данный логин уже занят")
            clientView.switchScreen(IClientView.LOGIN_SCREEN, listOf())
        }
    }

    fun saveButtonClicked(image: ImageView, data: HashMap<String, String>){
        if(image.tag != 0){
            //Конвертация Bitmap'а в строку, чтобы влез в базу данных
            val COMPRESSION_QUALITY = 100
            val encodedImage: String
            val byteArrayBitmapStream = ByteArrayOutputStream()
            image.drawable.toBitmap().compress(
                Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream
            )
            val b: ByteArray = byteArrayBitmapStream.toByteArray()
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            data.put("photo", encodedImage)
        } else data.put("photo", "empty")

        data.put("id", id)
        model.update(data)
        clientView.pushError("Сохранено")
    }
    fun registerButtonClicked(image: ImageView, data: HashMap<String, String>){
        if(image.tag != 0){
            //Конвертация Bitmap'а в строку, чтобы влез в базу данных
            val COMPRESSION_QUALITY = 100
            val encodedImage: String
            val byteArrayBitmapStream = ByteArrayOutputStream()
            image.drawable.toBitmap().compress(
                Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream
            )
            val b: ByteArray = byteArrayBitmapStream.toByteArray()
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            data.put("photo", encodedImage)
        } else data.put("photo", "empty")

        model.insert(data)
        clientView.pushError("Сохранено")
    }
    fun cancelButtonClicked(){
        clientView.switchScreen(IClientView.LOGIN_SCREEN, listOf())
    }
}