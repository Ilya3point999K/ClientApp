package com.ilya3point999k.clientapp

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class LocalDatabase(val context: Context) : IClientModel {

    private val dbHelper = DBHelper(context)

    override fun insert(data: HashMap<String, String>) {
            val cv = ContentValues()
            cv.put(Table.COLUMN.LOGIN, data.get(Table.COLUMN.LOGIN))
            cv.put(Table.COLUMN.PASSWORD, data.get(Table.COLUMN.PASSWORD))
            cv.put(Table.COLUMN.NAME, data.get(Table.COLUMN.NAME))
            cv.put(Table.COLUMN.SURNAME, data.get(Table.COLUMN.SURNAME))
            cv.put(Table.COLUMN.PATRONYM, data.get(Table.COLUMN.PATRONYM))
            cv.put(Table.COLUMN.GENDER, data.get(Table.COLUMN.GENDER))
            cv.put(Table.COLUMN.AGE, data.get(Table.COLUMN.AGE))
            cv.put(Table.COLUMN.TOWN, data.get(Table.COLUMN.TOWN))
            cv.put(Table.COLUMN.STREET, data.get(Table.COLUMN.STREET))
            cv.put(Table.COLUMN.BUILDING, data.get(Table.COLUMN.BUILDING))
            cv.put(Table.COLUMN.FLAT, data.get(Table.COLUMN.FLAT))
            cv.put(Table.COLUMN.ORGANIZATION, data.get(Table.COLUMN.ORGANIZATION))
            cv.put(Table.COLUMN.POST, data.get(Table.COLUMN.POST))
            cv.put(Table.COLUMN.PHOTO, data.get(Table.COLUMN.PHOTO))
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
                respond.put(Table.COLUMN.ID, cursor.getString(cursor.getColumnIndex(Table.COLUMN.ID)))
                respond.put(Table.COLUMN.NAME, cursor.getString(cursor.getColumnIndex(Table.COLUMN.NAME)))
                respond.put(Table.COLUMN.SURNAME, cursor.getString(cursor.getColumnIndex(Table.COLUMN.SURNAME)))
                respond.put(Table.COLUMN.PATRONYM, cursor.getString(cursor.getColumnIndex(Table.COLUMN.PATRONYM)))
                respond.put(Table.COLUMN.GENDER, cursor.getString(cursor.getColumnIndex(Table.COLUMN.GENDER)))
                respond.put(Table.COLUMN.AGE, cursor.getString(cursor.getColumnIndex(Table.COLUMN.AGE)))
                respond.put(Table.COLUMN.TOWN, cursor.getString(cursor.getColumnIndex(Table.COLUMN.TOWN)))
                respond.put(Table.COLUMN.STREET, cursor.getString(cursor.getColumnIndex(Table.COLUMN.STREET)))
                respond.put(Table.COLUMN.BUILDING, cursor.getString(cursor.getColumnIndex(Table.COLUMN.BUILDING)))
                respond.put(Table.COLUMN.FLAT, cursor.getString(cursor.getColumnIndex(Table.COLUMN.FLAT)))
                respond.put(Table.COLUMN.ORGANIZATION, cursor.getString(cursor.getColumnIndex(Table.COLUMN.ORGANIZATION)))
                respond.put(Table.COLUMN.POST, cursor.getString(cursor.getColumnIndex(Table.COLUMN.POST)))
                respond.put(Table.COLUMN.PHOTO, cursor.getString(cursor.getColumnIndex(Table.COLUMN.PHOTO)))
                break;
            }
        }
        cursor.close()
        return respond
    }

    override fun update(data: HashMap<String, String>) {
        val cv = ContentValues()
        cv.put(Table.COLUMN.NAME, data.get(Table.COLUMN.NAME))
        cv.put(Table.COLUMN.SURNAME, data.get(Table.COLUMN.SURNAME))
        cv.put(Table.COLUMN.PATRONYM, data.get(Table.COLUMN.PATRONYM))
        cv.put(Table.COLUMN.GENDER, data.get(Table.COLUMN.GENDER))
        cv.put(Table.COLUMN.AGE, data.get(Table.COLUMN.AGE))
        cv.put(Table.COLUMN.TOWN, data.get(Table.COLUMN.TOWN))
        cv.put(Table.COLUMN.STREET, data.get(Table.COLUMN.STREET))
        cv.put(Table.COLUMN.BUILDING, data.get(Table.COLUMN.BUILDING))
        cv.put(Table.COLUMN.FLAT, data.get(Table.COLUMN.FLAT))
        cv.put(Table.COLUMN.ORGANIZATION, data.get(Table.COLUMN.ORGANIZATION))
        cv.put(Table.COLUMN.POST, data.get(Table.COLUMN.POST))
        cv.put(Table.COLUMN.PHOTO, data.get(Table.COLUMN.PHOTO))
        dbHelper.writableDatabase.update(Table.TABLE, cv, Table.COLUMN.ID + " = ?", arrayOf(data.get(Table.COLUMN.ID)))
    }

    class DBHelper(val context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            try {
                db!!.execSQL(Table.CREATE_SCRIPT);
            } catch (e: SQLException){
                Log.println(Log.ERROR, "LD", e.stackTraceToString())
                Toast.makeText(context, "Не получилось создать таблицу", Toast.LENGTH_LONG).show()
            }
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