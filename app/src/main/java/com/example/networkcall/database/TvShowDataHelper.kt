package com.example.networkcall.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.networkcall.data.DataItem

class TvShowDataHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val DROP_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER ,$NAME TEXT,$DATE TEXT,$SHORT_DESCRIPTION TEXT,$IMAGE TEXT);"

        db!!.execSQL(DROP_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun checkIfExist(courseId: Int?): Boolean {
        val db = this.readableDatabase
        val query = "SELECT $ID FROM $TABLE_NAME WHERE $ID = '$courseId'; "
        val cursor2 = db.rawQuery(query, null)

        if (cursor2.count <= 0) {
            cursor2.close()
            return false
        }
        return true

    }
    fun add(item: DataItem): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(ID, item.id)
        values.put(NAME, item.name)
        values.put(DATE, item.airdate)
        values.put(SHORT_DESCRIPTION, item.summary)
        values.put(IMAGE, item.image)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }
    @SuppressLint("Range")
    fun getItem(): List<DataItem> {

        val cartItem = ArrayList<DataItem>()

        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor != null && cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    val item = DataItem()

                    item.id = cursor.getInt(cursor.getColumnIndex(ID))
                    item.name = cursor.getString(cursor.getColumnIndex(NAME))
                    item.airdate = cursor.getString(cursor.getColumnIndex(DATE))
                    item.summary = cursor.getString(cursor.getColumnIndex(SHORT_DESCRIPTION))
                    item.image = cursor.getString(cursor.getColumnIndex(IMAGE))

                    cartItem.add(item)
                } while (cursor.moveToNext())
            }

        }

        cursor.close()
        return cartItem
    }

    companion object{

        const val DATABASE_NAME = "TVShows_Database"
        const val DATABASE_VERSION = 1

        val TABLE_NAME = "tv_shows"
        const val ID = "id"
        const val NAME = "name"
        const val DATE = "date"
        const val SHORT_DESCRIPTION = "description"
        const val IMAGE = "image"
    }
}