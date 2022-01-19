package com.ilya3point999k.clientapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.logging.Logger

class LocalDatabase(context: Context) : IClientModel {

    private val dbHelper = DBHelper(context)

    override fun insert(data: HashMap<String, String>) {
            val cv = ContentValues()
            cv.put(Table.COLUMN.LOGIN, data.get("login"))
            cv.put(Table.COLUMN.PASSWORD, data.get("password"))
            cv.put(Table.COLUMN.NAME, data.get("name"))
            cv.put(Table.COLUMN.SURNAME, data.get("surname"))
            cv.put(Table.COLUMN.PATRONYM, data.get("patronym"))
            cv.put(Table.COLUMN.GENDER, data.get("gender"))
            cv.put(Table.COLUMN.AGE, data.get("age"))
            cv.put(Table.COLUMN.TOWN, data.get("town"))
            cv.put(Table.COLUMN.STREET, data.get("street"))
            cv.put(Table.COLUMN.BUILDING, data.get("building"))
            cv.put(Table.COLUMN.FLAT, data.get("flat"))
            cv.put(Table.COLUMN.ORGANIZATION, data.get("organization"))
            cv.put(Table.COLUMN.POST, data.get("post"))
            cv.put(Table.COLUMN.PHOTO, data.get("photo"))
            dbHelper.writableDatabase.insert(Table.TABLE, null, cv)
    }

    override fun get(request: List<String>): HashMap<String, String> {
        val cursor = dbHelper.readableDatabase.query(
            Table.TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val respond = HashMap<String, String>()
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(Table.COLUMN.LOGIN)) == request[0] &&
                cursor.getString(cursor.getColumnIndex(Table.COLUMN.PASSWORD)) != request[1])
            {
                respond.put("id", "collision")
                break
            }
            if(cursor.getString(cursor.getColumnIndex(Table.COLUMN.LOGIN)) == request[0] &&
                cursor.getString(cursor.getColumnIndex(Table.COLUMN.PASSWORD)) == request[1]){
                respond.put("id", cursor.getString(cursor.getColumnIndex(Table.COLUMN.ID)))
                respond.put("name", cursor.getString(cursor.getColumnIndex(Table.COLUMN.NAME)))
                respond.put("surname", cursor.getString(cursor.getColumnIndex(Table.COLUMN.SURNAME)))
                respond.put("patronym", cursor.getString(cursor.getColumnIndex(Table.COLUMN.PATRONYM)))
                respond.put("gender", cursor.getString(cursor.getColumnIndex(Table.COLUMN.GENDER)))
                respond.put("age", cursor.getString(cursor.getColumnIndex(Table.COLUMN.AGE)))
                respond.put("town", cursor.getString(cursor.getColumnIndex(Table.COLUMN.TOWN)))
                respond.put("street", cursor.getString(cursor.getColumnIndex(Table.COLUMN.STREET)))
                respond.put("building", cursor.getString(cursor.getColumnIndex(Table.COLUMN.BUILDING)))
                respond.put("flat", cursor.getString(cursor.getColumnIndex(Table.COLUMN.FLAT)))
                respond.put("organization", cursor.getString(cursor.getColumnIndex(Table.COLUMN.ORGANIZATION)))
                respond.put("post", cursor.getString(cursor.getColumnIndex(Table.COLUMN.POST)))
                respond.put("photo", cursor.getString(cursor.getColumnIndex(Table.COLUMN.PHOTO)))
                break;
            }
        }
        cursor.close()
        return respond
    }

    override fun update(data: HashMap<String, String>) {
        val cv = ContentValues()
        cv.put(Table.COLUMN.NAME, data.get("name"))
        cv.put(Table.COLUMN.SURNAME, data.get("surname"))
        cv.put(Table.COLUMN.PATRONYM, data.get("patronym"))
        cv.put(Table.COLUMN.GENDER, data.get("gender"))
        cv.put(Table.COLUMN.AGE, data.get("age"))
        cv.put(Table.COLUMN.TOWN, data.get("town"))
        cv.put(Table.COLUMN.STREET, data.get("street"))
        cv.put(Table.COLUMN.BUILDING, data.get("building"))
        cv.put(Table.COLUMN.FLAT, data.get("flat"))
        cv.put(Table.COLUMN.ORGANIZATION, data.get("organization"))
        cv.put(Table.COLUMN.POST, data.get("post"))
        cv.put(Table.COLUMN.PHOTO, data.get("photo"))
        Log.w("Loggbef", data.get("name")!!)
        dbHelper.writableDatabase.update(Table.TABLE, cv, "id = ?", arrayOf(data.get("id")))
    }

    class DBHelper(val context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(Table.CREATE_SCRIPT);
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }

        companion object {
            const val NAME = "CLIENTLOCAL"
            const val VERSION = 1
        }
    }

    object Table {
        val TABLE = "users"


        object COLUMN {
            val ID = "id"
            val LOGIN = "login"
            val PASSWORD = "password"
            val NAME = "name"
            val SURNAME = "surname"
            val PATRONYM = "patronym"
            val GENDER = "gender"
            val AGE = "age"
            val TOWN = "town"
            val STREET = "street"
            val BUILDING = "building"
            val FLAT = "flat"
            val ORGANIZATION = "organization"
            val POST = "post"
            val PHOTO = "photo"
        }


        val CREATE_SCRIPT = String.format(
            "create table %s ("
                    + "%s integer primary key autoincrement,"
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s integer, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text, "
                    + "%s text"
                    + ");",
            TABLE, COLUMN.ID, COLUMN.LOGIN,
            COLUMN.PASSWORD, COLUMN.NAME, COLUMN.SURNAME,
            COLUMN.PATRONYM, COLUMN.GENDER, COLUMN.AGE,
            COLUMN.TOWN, COLUMN.STREET, COLUMN.BUILDING,
            COLUMN.FLAT, COLUMN.ORGANIZATION, COLUMN.POST, COLUMN.PHOTO
        )
    }

}