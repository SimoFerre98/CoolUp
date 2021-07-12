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
        }
        //  Metodo che serve per quando si fanno gli agiornamenti del DB
        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int, newVersion: Int
        ) {
            Log.d("CoolUp", "aggiornamento della versione del db dalla versione: " + oldVersion + " alla " + newVersion )
            db.execSQL(DROP_FRIDGE_TABLE)
            db.execSQL(DROP_DOWNLOAD_TABLE)
            db.execSQL(DROP_SHOPLIST_TABLE)
            onCreate(db)
        }
    }

    // database and database helper objects
    private var db: SQLiteDatabase? = null
    private val dbHelper: DBHelper

    // Metodo privato che serve per aprire una coinnessione di solo lettura con il DB
    private fun openReadableDB() {
        db = dbHelper.readableDatabase
    }

    // Metodo privato che serve per aprire una coinnessione di lettura e scritturacon il DB
    private fun openWriteableDB() {
        db = dbHelper.writableDatabase
    }

    //Metodo che chiude la connessione con il DB
    private fun closeDB() {
        if (db != null) db!!.close()
    }

    //Metodo chye dealloca il cursore
    private fun CloseCursor(cursor: Cursor?) {
        if (cursor == null) {
            cursor?.close()
        }
    }

    //  Metodo che restituisce la dimensione della table, cioè il numero massimo di tuple
    fun DimDownloadTable():Int
    {
        return FRIDGE_TABLE.length
    }

    //  Metodo che restituisce un arraylist di tutti i valori dentro il db FRIGO
    val FridgeLists: ArrayList<dbfridge> get() {
        val lists: ArrayList<dbfridge> = ArrayList<dbfridge>()

        openReadableDB()
        val cursor = db!!.query( FRIDGE_TABLE,null, null, null, null, null, null )
        while (cursor.moveToNext()) {
            val list = dbfridge()

            list.SetName(cursor.getString(FRIDGE_NAME_COL))
            list.SetCategory(cursor.getString(FRIDGE_CATEGORY_COL))
            list.SetAllergens(cursor.getString(FRIDGE_ALLERGENS_COL))
            list.SetDescription(cursor.getString(FRIDGE_DESCRIPTION_COL))
            list.SetUnit(cursor.getString(FRIDGE_UNIT_COL))
            list.SetRecyclable(cursor.getString(FRIDGE_RECYCLABLE_COL))
            list.SetFreezable(cursor.getString(FRIDGE_FREEZABLE_COL))
            list.SetDate(cursor.getString(FRIDGE_DATE_COL))
            list.SetEAN(cursor.getString(FRIDGE_EAN_COL))

            lists.add(list)
        }
        CloseCursor(cursor)
        closeDB()
        return lists
    }

    //  Metodo che restituisce un arraylist di tutti i valori dentro il db DOWNLOAD
    val DownloadLists: ArrayList<dbdownload> get() {
        val lists: ArrayList<dbdownload> = ArrayList<dbdownload>()

        openReadableDB()
        val cursor = db!!.query( DOWNLOAD_TABLE,null, null, null, null, null, null )
        while (cursor.moveToNext()) {
            val list = dbdownload()
            list.SetName(cursor.getString(FRIDGE_NAME_COL))
            list.SetCategory(cursor.getString(FRIDGE_CATEGORY_COL))
            list.SetAllergens(cursor.getString(FRIDGE_ALLERGENS_COL))
            list.SetDescription(cursor.getString(FRIDGE_DESCRIPTION_COL))
            list.SetUnit(cursor.getString(FRIDGE_UNIT_COL))
            list.SetRecyclable(cursor.getString(FRIDGE_RECYCLABLE_COL))
            list.SetFreezable(cursor.getString(FRIDGE_FREEZABLE_COL))
            list.SetEAN(cursor.getString(FRIDGE_EAN_COL))
            lists.add(list)
        }
        CloseCursor(cursor)
        closeDB()
        return lists
    }

    //  Metodo che restituisce un arraylist di tutti i valori dentro il db SHOPLIST
    val ShopListLists: ArrayList<dblist> get() {
        val lists: ArrayList<dblist> = ArrayList<dblist>()

        openReadableDB()
        val cursor = db!!.query( SHOPLIST_TABLE,null, null, null, null, null, null )
        while (cursor.moveToNext()) {
            val list = dblist()
            list.SetName(cursor.getString(FRIDGE_NAME_COL))
            list.SetEAN(cursor.getString(FRIDGE_EAN_COL))
            lists.add(list)
        }
        CloseCursor(cursor)
        closeDB()
        return lists
    }

    fun GetFridgeList(Name: String): dbfridge? {
        val where = FRIDGE_TABLE + "= ?"
        val whereArgs = arrayOf(Name)
        openReadableDB()
        val cursor = db!!.query(FRIDGE_TABLE, null, where, whereArgs, null, null, null )
        var list: dbfridge? = null
        cursor.moveToFirst()
        list = dbfridge(

            cursor.getString(FRIDGE_NAME_COL),
            cursor.getString(FRIDGE_CATEGORY_COL),
            cursor.getString(FRIDGE_ALLERGENS_COL),
            cursor.getString(FRIDGE_DESCRIPTION_COL),
            cursor.getString(FRIDGE_UNIT_COL),
            cursor.getString(FRIDGE_RECYCLABLE_COL),
            cursor.getString(FRIDGE_FREEZABLE_COL),
            cursor.getString(FRIDGE_DATE_COL),
            cursor.getString(FRIDGE_EAN_COL)
        )
        CloseCursor(cursor)
        closeDB()
        return list
    }/*
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
*/
    //  Metodo che ritorna il singolo oggetto di frtigo in base all'id
    fun GetFrigo(Id: Int): dbfridge? {
        val where = FRIDGE_ID + "= ?"
        val whereArgs = arrayOf(Integer.toString(Id))
        openReadableDB()
        val cursor = db!!.query(FRIDGE_TABLE,null, where, whereArgs, null, null, null)
        cursor.moveToFirst()
        val task: dbfridge? = getFridgeFromCursor(cursor)
        CloseCursor(cursor)
        closeDB()
        return task
    }

    //  Metodo che ritorna il singolo oggetto di download in base all'EAN
    fun GetDownload(EAN: String): dbdownload? {
        val where = DOWNLOAD_EAN + "= ?"
        val whereArgs = arrayOf(EAN)
        openReadableDB()
        val cursor = db!!.query(DOWNLOAD_TABLE,null, where, whereArgs, null, null, null)
        cursor.moveToFirst()
        val download: dbdownload? = getDownloadFromCursor(cursor)
        CloseCursor(cursor)
        closeDB()
        return download
    }

    //  Metodo che ritorna il singolo oggetto di shoplist in base all'EAN
    fun GetShopList(EAN: String): dblist? {
        val where = SHOPLIST_TABLE + "= ?"
        val whereArgs = arrayOf(EAN)
        openReadableDB()
        val cursor = db!!.query(SHOPLIST_TABLE,null, where, whereArgs, null, null, null)
        cursor.moveToFirst()
        val shoplist: dblist? = getShopListFromCursor(cursor)
        CloseCursor(cursor)
        closeDB()
        return shoplist
    }


    //  Metodo che inserisce un oggetto nel db
    fun InsertFridge(fridge: dbfridge): Long {
        val cv = ContentValues()
        cv.put(FRIDGE_EAN, fridge.GetEAN())
        cv.put(FRIDGE_NAME, fridge.GetName())
        cv.put(FRIDGE_CATEGORY, fridge.GetCategory())
        cv.put(FRIDGE_DESCRIPTION, fridge.GetDescription())
        cv.put(FRIDGE_ALLERGENS, fridge.GetAllergens())
        cv.put(FRIDGE_UNIT, fridge.GetUnit())
        cv.put(FRIDGE_RECYCLABLE, fridge.GetRecyclable())
        cv.put(FRIDGE_FREEZABLE, fridge.GetFreezable())
        cv.put(FRIDGE_DATE, fridge.GetDate())
        openWriteableDB()

        val rowID = db!!.insert(FRIDGE_TABLE, null, cv)
        closeDB()
        return rowID
    }

    //  Metodo che inserisce dentro il database download una tupla
    fun InsertDownload(fridge: dbfridge): Long {
        val cv = ContentValues()
        cv.put(FRIDGE_EAN, fridge.GetEAN())
        cv.put(FRIDGE_NAME, fridge.GetName())
        cv.put(FRIDGE_CATEGORY, fridge.GetCategory())
        cv.put(FRIDGE_DESCRIPTION, fridge.GetDescription())
        cv.put(FRIDGE_ALLERGENS, fridge.GetAllergens())
        cv.put(FRIDGE_UNIT, fridge.GetUnit())
        cv.put(FRIDGE_RECYCLABLE, fridge.GetRecyclable())
        cv.put(FRIDGE_FREEZABLE, fridge.GetFreezable())
        cv.put(FRIDGE_DATE, fridge.GetDate())
        openWriteableDB()

        val rowID = db!!.insert(DOWNLOAD_TABLE, null, cv)
        closeDB()
        return rowID
    }

    //  Metodo che inserisce dentro il database download una tupla
    fun InsertShopList(fridge: dbfridge): Long {
        val cv = ContentValues()
        cv.put(FRIDGE_EAN, fridge.GetEAN())
        cv.put(FRIDGE_NAME, fridge.GetName())
        openWriteableDB()

        val rowID = db!!.insert(SHOPLIST_TABLE, null, cv)
        closeDB()
        return rowID
    }


    //  Metodo che aggiorna un oggetto Frigo
    fun UpdateFridge(fridge: dbfridge): Int {
        val cv = ContentValues()
        //cv.put(FRIDGE_ID, fridge.GetId())
        cv.put(FRIDGE_EAN, fridge.GetEAN())
        cv.put(FRIDGE_NAME, fridge.GetName())
        cv.put(FRIDGE_CATEGORY, fridge.GetCategory())
        cv.put(FRIDGE_DESCRIPTION, fridge.GetDescription())
        cv.put(FRIDGE_ALLERGENS, fridge.GetAllergens())
        cv.put(FRIDGE_UNIT, fridge.GetUnit())
        cv.put(FRIDGE_RECYCLABLE, fridge.GetRecyclable())
        cv.put(FRIDGE_FREEZABLE, fridge.GetFreezable())
        cv.put(FRIDGE_DATE, fridge.GetDate())
        val where = FRIDGE_ID + "= ?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(fridge.GetName()))
        openWriteableDB()
        val rowCount = db!!.update(FRIDGE_TABLE, cv, where, whereArgs)
        closeDB()
        return rowCount
    }

    //  Metodo che elimina un un oggetto frigo dalla table
    fun DeleteFridge(id: Int): Int {
        val where = FRIDGE_ID + "= ?"
        val whereArgs = arrayOf(id.toString())
        openWriteableDB()
        val rowCount = db!!.delete(FRIDGE_TABLE, where, whereArgs)
        closeDB()
        return rowCount
    }

    private fun getFridgeFromCursor(cursor: Cursor?): dbfridge? {
        if (cursor == null || cursor.count == 0) {
            return null
        } else {
            try {
                val fridge = dbfridge(
                    cursor.getString(FRIDGE_NAME_COL),
                    cursor.getString(FRIDGE_CATEGORY_COL),
                    cursor.getString(FRIDGE_ALLERGENS_COL),
                    cursor.getString(FRIDGE_DESCRIPTION_COL),
                    cursor.getString(FRIDGE_UNIT_COL),
                    cursor.getString(FRIDGE_RECYCLABLE_COL),
                    cursor.getString(FRIDGE_FREEZABLE_COL),
                    cursor.getString(FRIDGE_DATE_COL),
                    cursor.getString(FRIDGE_EAN_COL)
                )
                return fridge
            } catch (e: Exception) {
                null
            }
        }
        return null
    }
    private fun getDownloadFromCursor(cursor: Cursor?): dbdownload? {
        if (cursor == null || cursor.count == 0) {
            return null
        } else {
            try {
                val download = dbdownload(
                    cursor.getString(DOWNLOAD_NAME_COL),
                    cursor.getString(DOWNLOAD_DESCRIPTION_COL),
                    cursor.getString(DOWNLOAD_ALLERGENS_COL),
                    cursor.getString(DOWNLOAD_DESCRIPTION_COL),
                    cursor.getString(DOWNLOAD_UNIT_COL),
                    cursor.getString(DOWNLOAD_RECYCLABLE_COL),
                    cursor.getString(DOWNLOAD_FREEZABLE_COL),
                    cursor.getString(DOWNLOAD_EAN_COL)
                )
                return download
            } catch (e: Exception) {
                null
            }
        }
        return null
    }
    private fun getShopListFromCursor(cursor: Cursor?): dblist? {
        if (cursor == null || cursor.count == 0) {
            return null
        } else {
            try {
                val list = dblist(
                    cursor.getString(SHOPLIST_EAN_COL),
                    cursor.getString(SHOPLIST_NAME_COL)
                )
                return list
            } catch (e: Exception) {
                null
            }
        }
        return null
    }

    //  Variabili costanti per la gestione del Db
    companion object {
        // Costanti del database
        const val DB_NAME = "CoolUp.db"
        const val DB_VERSION = 1

        // Lista delle table
        private const val FRIDGE_TABLE = "fridge"
        private const val DOWNLOAD_TABLE = "download"
        private const val SHOPLIST_TABLE = "shoplist"

        //  Variabili della tabella FRIGO
        private const val FRIDGE_ID = "Id"
        private const val FRIDGE_ID_COL = 0
        private const val FRIDGE_EAN = "EAN"
        private const val FRIDGE_EAN_COL = 1
        private const val FRIDGE_NAME = "Name"
        private const val FRIDGE_NAME_COL = 2
        private const val FRIDGE_CATEGORY = "Category"
        private const val FRIDGE_CATEGORY_COL = 3
        private const val FRIDGE_DESCRIPTION= "Description"
        private const val FRIDGE_DESCRIPTION_COL = 4
        private const val FRIDGE_ALLERGENS= "Allergens"
        private const val FRIDGE_ALLERGENS_COL = 5
        private const val FRIDGE_UNIT = "Unit"
        private const val FRIDGE_UNIT_COL = 6
        private const val FRIDGE_RECYCLABLE = "Recyclable"
        private const val FRIDGE_RECYCLABLE_COL = 7
        private const val FRIDGE_FREEZABLE = "freezable"
        private const val FRIDGE_FREEZABLE_COL = 8
        private const val FRIDGE_DATE = "Date"
        private const val FRIDGE_DATE_COL = 9

        //  Variabili della tabella DOWNLOAD
        private const val DOWNLOAD_EAN = "EAN"
        private const val DOWNLOAD_EAN_COL = 0
        private const val DOWNLOAD_NAME = "Name"
        private const val DOWNLOAD_NAME_COL = 1
        private const val DOWNLOAD_CATEGORY = "Category"
        private const val DOWNLOAD_CATEGORY_COL = 2
        private const val DOWNLOAD_DESCRIPTION= "Description"
        private const val DOWNLOAD_DESCRIPTION_COL = 3
        private const val DOWNLOAD_ALLERGENS= "Allergens"
        private const val DOWNLOAD_ALLERGENS_COL = 4
        private const val DOWNLOAD_UNIT = "Unit"
        private const val DOWNLOAD_UNIT_COL = 5
        private const val DOWNLOAD_RECYCLABLE = "Recyclable"
        private const val DOWNLOAD_RECYCLABLE_COL = 6
        private const val DOWNLOAD_FREEZABLE = "freezable"
        private const val DOWNLOAD_FREEZABLE_COL = 7

        //  Variabili per la tabbella SHOPLIST
        private const val SHOPLIST_EAN = "EAN"
        private const val SHOPLIST_EAN_COL = 0
        private const val SHOPLIST_NAME = "Name"
        private const val SHOPLIST_NAME_COL = 1

        // CREATE ae DROP TABLE di tutte le tabelle
        private const val CREATE_FRIDGE_TABLE = "CREATE TABLE " + FRIDGE_TABLE + " (" +
                FRIDGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FRIDGE_EAN + " TEXT NOT NULL, " +
                FRIDGE_NAME + " TEXT, " +
                FRIDGE_CATEGORY + " TEXT, " +
                FRIDGE_DESCRIPTION + " TEXT, " +
                FRIDGE_ALLERGENS + " TEXT, " +
                FRIDGE_UNIT + " TEXT, " +
                FRIDGE_RECYCLABLE + " TEXT, " +
                FRIDGE_FREEZABLE + " TEXT, " +
                FRIDGE_DATE + " TEXT)"


        private const val CREATE_DOWNLOAD_TABLE = "CREATE TABLE " + DOWNLOAD_TABLE + " (" +
                DOWNLOAD_EAN + " TEXT NOT NULL, " +
                DOWNLOAD_NAME + " TEXT, " +
                DOWNLOAD_CATEGORY + " TEXT, " +
                DOWNLOAD_DESCRIPTION + " TEXT, " +
                DOWNLOAD_ALLERGENS + " TEXT, " +
                DOWNLOAD_UNIT + " TEXT, " +
                DOWNLOAD_RECYCLABLE + " TEXT, " +
                DOWNLOAD_FREEZABLE + " TEXT)"

        private const val CREATE_SHOPLIST_TABLE = "CREATE TABLE " + SHOPLIST_TABLE + " (" +
                FRIDGE_EAN + " TEXT NOT NULL, " +
                DOWNLOAD_NAME + " TEXT)"


        private const val DROP_FRIDGE_TABLE = "DROP TABLE IF EXISTS " + FRIDGE_TABLE

        private const val DROP_DOWNLOAD_TABLE = "DROP TABLE IF EXISTS " + DOWNLOAD_TABLE

        private const val DROP_SHOPLIST_TABLE = "DROP TABLE IF EXISTS " + SHOPLIST_TABLE


    }

    // constructor
    init {
        dbHelper = DBHelper(context, DB_NAME, null, DB_VERSION)
    }
}