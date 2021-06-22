package com.Simo_Elia.CoolUp
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbhelper (private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EAN + "TEXT, " +
                COLUMN_NAME_PRODUCT + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_ETICHETTA + " TEXT, " +
                COLUMN_DIFFERENZIATA + " TEXT, " +
                COLUMN_ALLERGENE + " TEXT, " +
                COLUMN_SURGELATO + " BOOLEAN);"
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    companion object {
        private const val DATABASE_NAME = "CoolUp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "FrigoList"
        //  Attributi
        private const val COLUMN_ID = "id"
        private const val COLUMN_EAN = "EAN_code"
        private const val COLUMN_NAME_PRODUCT = "name"
        private const val COLUMN_CATEGORY = "categoria"
        private const val COLUMN_ETICHETTA = "etichetta"
        private const val COLUMN_DIFFERENZIATA = "raccolta_differenziata"
        private const val COLUMN_ALLERGENE = "allergene"
        private const val COLUMN_SURGELATO = true
    }
}
