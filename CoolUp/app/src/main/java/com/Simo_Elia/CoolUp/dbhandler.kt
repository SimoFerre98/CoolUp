package com.Simo_Elia.CoolUp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList


class dbhandler(context: Context?) {
    //  DBHelper che estende SQLiteOpenHelper per la gestione del database
    private class DBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase) {
            // create tables
            db.execSQL(CREATE_FRIDGE_TABLE)
            db.execSQL(CREATE_DOWNLOAD_TABLE)
            db.execSQL(CREATE_SHOPLIST_TABLE)

            // insert default lists
            db.execSQL("INSERT INTO list VALUES (1, 'Personal')")
            db.execSQL("INSERT INTO list VALUES (2, 'Business')")

            // insert sample tasks
            db.execSQL(
                """
                    INSERT INTO task VALUES (1, 1, 'Pay bills', 'Rent
                    Phone
                    Internet', 0, 0)
                    """.trimIndent()
            )
            db.execSQL(
                "INSERT INTO task VALUES (2, 1, 'Get hair cut', " +
                        "'', 0, 0)"
            )
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int, newVersion: Int
        ) {
            Log.d(
                "Task list", "Upgrading db from version "
                        + oldVersion + " to " + newVersion
            )
            db.execSQL(DROP_LIST_TABLE)
            db.execSQL(DROP_TASK_TABLE)
            onCreate(db)
        }
    }

    // database and database helper objects
    private var db: SQLiteDatabase? = null
    private val dbHelper: DBHelper

    // private methods
    private fun openReadableDB() {
        db = dbHelper.readableDatabase
    }

    private fun openWriteableDB() {
        db = dbHelper.writableDatabase
    }

    private fun closeDB() {
        if (db != null) db!!.close()
    }

    private fun CloseCursor(cursor: Cursor?) {
        if (cursor == null) {
            cursor!!.close()
        }
    }

    // public methods
    val lists: ArrayList<Any>
        get() {
            val lists: ArrayList<List> = ArrayList<List>()
            openReadableDB()
            val cursor = db!!.query(
                LIST_TABLE,
                null, null, null, null, null, null
            )
            while (cursor.moveToNext()) {
                val list = List<Any>()
                list.setId(cursor.getInt(LIST_ID_COL))
                list.setName(cursor.getString(LIST_NAME_COL))
                lists.add(list)
            }
            CloseCursor(cursor)
            closeDB()
            return lists
        }

    fun getList(name: String): List? {
        val where = LIST_NAME + "= ?"
        val whereArgs = arrayOf(name)
        openReadableDB()
        val cursor = db!!.query(
            LIST_TABLE, null,
            where, whereArgs, null, null, null
        )
        var list: List? = null
        cursor.moveToFirst()
        list = List(
            cursor.getInt(LIST_ID_COL),
            cursor.getString(LIST_NAME_COL)
        )
        CloseCursor(cursor)
        closeDB()
        return list
    }

    fun getTasks(listName: String): ArrayList<Task?> {
        val where = TASK_LIST_ID + "= ? AND " +
                TASK_HIDDEN + "!=1"
        val listID: Int = getList(listName).getId()
        val whereArgs = arrayOf(Integer.toString(listID))
        openReadableDB()
        val cursor = db!!.query(
            TASK_TABLE, null,
            where, whereArgs,
            null, null, null
        )
        val tasks: ArrayList<Task?> = ArrayList<Task?>()
        while (cursor.moveToNext()) {
            tasks.add(getTaskFromCursor(cursor))
        }
        CloseCursor(cursor)
        closeDB()
        return tasks
    }

    fun getTask(id: Int): Task? {
        val where = TASK_ID + "= ?"
        val whereArgs = arrayOf(Integer.toString(id))
        openReadableDB()
        val cursor = db!!.query(
            TASK_TABLE,
            null, where, whereArgs, null, null, null
        )
        cursor.moveToFirst()
        val task: Task? = getTaskFromCursor(cursor)
        CloseCursor(cursor)
        closeDB()
        return task
    }

    fun insertTask(task: Task): Long {
        val cv = ContentValues()
        cv.put(TASK_LIST_ID, task.getListId())
        cv.put(TASK_NAME, task.getName())
        cv.put(TASK_NOTES, task.getNotes())
        cv.put(TASK_COMPLETED, task.getCompletedDate())
        cv.put(TASK_HIDDEN, task.getHidden())
        openWriteableDB()
        val rowID = db!!.insert(TASK_TABLE, null, cv)
        closeDB()
        return rowID
    }

    fun updateTask(task: Task): Int {
        val cv = ContentValues()
        cv.put(TASK_LIST_ID, task.getListId())
        cv.put(TASK_NAME, task.getName())
        cv.put(TASK_NOTES, task.getNotes())
        cv.put(TASK_COMPLETED, task.getCompletedDate())
        cv.put(TASK_HIDDEN, task.getHidden())
        val where = TASK_ID + "= ?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(task.getId()))
        openWriteableDB()
        val rowCount = db!!.update(TASK_TABLE, cv, where, whereArgs)
        closeDB()
        return rowCount
    }

    fun deleteTask(id: Long): Int {
        val where = TASK_ID + "= ?"
        val whereArgs = arrayOf(id.toString())
        openWriteableDB()
        val rowCount = db!!.delete(TASK_TABLE, where, whereArgs)
        closeDB()
        return rowCount
    }

    //  Variabili costanti per la gestione del Db
    companion object {
        // Costanti del database
        const val DB_NAME = "CoolUp.db"
        const val DB_VERSION = 1

        // Lista delle table
        const val FRIDGE_TABLE = "fridge"
        const val DOWNLOAD_TABLE = "download"
        const val SHOPLIST_TABLE = "shoplist"

        //  Variabili della tabella FRIGO
        const val FRIDGE_ID = "Id"
        const val FRIDGE_ID_COL = 0
        const val FRIDGE_EAN = "EAN"
        const val FRIDGE_EAN_COL = 1
        const val FRIDGE_NAME = "Name"
        const val FRIDGE_NAME_COL = 2
        const val FRIDGE_CATEGORY = "Category"
        const val FRIDGE_CATEGORY_COL = 3
        const val FRIDGE_DESCRIPTION= "Description"
        const val FRIDGE_DESCRIPTION_COL = 4
        const val FRIDGE_ALLERGENS= "Allergens"
        const val FRIDGE_ALLERGENS_COL = 5
        const val FRIDGE_UNIT = "Unit"
        const val FRIDGE_UNIT_COL = 6
        const val FRIDGE_RECYCLABLE = "Recyclable"
        const val FRIDGE_RECYCLABLE_COL = 7
        const val FRIDGE_FREEZABLE = "freezable"
        const val FRIDGE_FREEZABLE_COL = 8
        const val FRIDGE_DATE = "Date"
        const val FRIDGE_DATE_COL = 9

        //  Variabili della tabella DOWNLOAD
        const val DOWNLOAD_EAN = "EAN"
        const val DOWNLOAD_EAN_COL = 0
        const val DOWNLOAD_NAME = "Name"
        const val DOWNLOAD_NAME_COL = 1
        const val DOWNLOAD_CATEGORY = "Category"
        const val DOWNLOAD_CATEGORY_COL = 2
        const val DOWNLOAD_DESCRIPTION= "Description"
        const val DOWNLOAD_DESCRIPTION_COL = 3
        const val DOWNLOAD_ALLERGENS= "Allergens"
        const val DOWNLOAD_ALLERGENS_COL = 4
        const val DOWNLOAD_UNIT = "Unit"
        const val DOWNLOAD_UNIT_COL = 5
        const val DOWNLOAD_RECYCLABLE = "Recyclable"
        const val DOWNLOAD_RECYCLABLE_COL = 6
        const val DOWNLOAD_FREEZABLE = "freezable"
        const val DOWNLOAD_FREEZABLE_COL = 7

        //  Variabili per la tabbella SHOPLIST
        const val SHOPLIST_EAN = "EAN"
        const val SHOPLIST_EAN_COL = 0
        const val SHOPLIST_NAME = "Name"
        const val SHOPLIST_NAME_COL = 1

        // CREATE ae DROP TABLE di tutte le tabelle
        const val CREATE_FRIDGE_TABLE = "CREATE TABLE " + FRIDGE_TABLE + " (" +
                FRIDGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FRIDGE_EAN + " TEXT    NOT NULL UNIQUE " +
                DOWNLOAD_NAME + " TEXT, " +
                DOWNLOAD_CATEGORY + " TEXT, " +
                DOWNLOAD_DESCRIPTION + " TEXT, " +
                DOWNLOAD_ALLERGENS + " TEXT, " +
                DOWNLOAD_UNIT + " TEXT, " +
                DOWNLOAD_RECYCLABLE + " TEXT, " +
                DOWNLOAD_FREEZABLE + " TEXT " +
                FRIDGE_DATE + " TEXT);"


        const val CREATE_DOWNLOAD_TABLE = "CREATE TABLE " + DOWNLOAD_TABLE + " (" +
                FRIDGE_EAN + " TEXT    NOT NULL UNIQUE " +
                DOWNLOAD_NAME + " TEXT, " +
                DOWNLOAD_CATEGORY + " TEXT, " +
                DOWNLOAD_DESCRIPTION + " TEXT, " +
                DOWNLOAD_ALLERGENS + " TEXT, " +
                DOWNLOAD_UNIT + " TEXT, " +
                DOWNLOAD_RECYCLABLE + " TEXT, " +
                DOWNLOAD_FREEZABLE + " TEXT);"

        const val CREATE_SHOPLIST_TABLE = "CREATE TABLE " + SHOPLIST_TABLE + " (" +
                FRIDGE_EAN + " TEXT    NOT NULL UNIQUE " +
                DOWNLOAD_NAME + " TEXT);"


        const val CREATE_FRIDGE_TABLE = "DROP TABLE IF EXISTS " + FRIDGE_TABLE

        const val CREATE_DOWNLOAD_TABLE = "DROP TABLE IF EXISTS " + DOWNLOAD_TABLE

        const val CREATE_SHOPLIST_TABLE = "DROP TABLE IF EXISTS " + SHOPLIST_TABLE

        private fun getTaskFromCursor(cursor: Cursor?): Task? {
            return if (cursor == null || cursor.count == 0) {
                null
            } else {
                try {
                    Task(
                        cursor.getInt(TASK_ID_COL),
                        cursor.getInt(TASK_LIST_ID_COL),
                        cursor.getString(TASK_NAME_COL),
                        cursor.getString(TASK_NOTES_COL),
                        cursor.getLong(TASK_COMPLETED_COL),
                        cursor.getInt(TASK_HIDDEN_COL)
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    // constructor
    init {
        dbHelper = DBHelper(context, DB_NAME, null, DB_VERSION)
    }
}