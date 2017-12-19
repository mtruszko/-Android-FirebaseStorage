package com.example.maro.productlistproj1

import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns

object ToDoContract {
    val CONTENT_AUTHORITY = "com.maro.todo"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://${CONTENT_AUTHORITY}")
    val TASK_PATH = TaskEntry.TABLE_NAME

    object TaskEntry {
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(TASK_PATH).build()
        val CONTENT_TYPE = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/${CONTENT_AUTHORITY}/${TASK_PATH}"
        val CONTENT_ITEM_TYPE = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/${CONTENT_AUTHORITY}/${TASK_PATH}"

        val TABLE_NAME = "todo"

        val KEY_ID = BaseColumns._ID
        val ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT"
        val ID_COLUMN = 0

        val KEY_DESCRIPTION = "description"
        val DESCRIPTION_OPTIONS = "TEXT NOT NULL"
        val DESCRIPTION_COLUMN = 1

        val KEY_AMOUNT = "amount"
        val AMOUNT_OPTIONS = "INTEGER DEFAULT 1"
        val AMOUNT_COLUMN = 2

        val KEY_PRICE = "price"
        val PRICE_OPTIONS = "INTEGER DEFAULT 1"
        val PRICE_COLUMN = 3

        val KEY_COMPLETED = "completed"
        val COMPLETED_OPTIONS = "INTEGER DEFAULT 0"
        val COMPLETED_COLUMN = 4

        val _COUNT = BaseColumns._COUNT

        fun buildWithId(id: Long): Uri {
            return ContentUris.withAppendedId(CONTENT_URI, id)
        }

        fun getIdFromUri(uri: Uri): Long {
            return ContentUris.parseId(uri)
        }
    }
}

class ToDoDbHelper(context: Context?)
    : SQLiteOpenHelper(context,
        ToDoDbHelper.DATABASE_NAME,
        null,
        ToDoDbHelper.DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "database.db"
        val DATABASE_VERSION = 2

        private val DB_CREATE_TODO_TABLE = "CREATE TABLE " + ToDoContract.TaskEntry.TABLE_NAME + "( " +
                ToDoContract.TaskEntry.KEY_ID + " " + ToDoContract.TaskEntry.ID_OPTIONS + ", " +
                ToDoContract.TaskEntry.KEY_DESCRIPTION + " " + ToDoContract.TaskEntry.DESCRIPTION_OPTIONS + ", " +
                ToDoContract.TaskEntry.KEY_AMOUNT + " " + ToDoContract.TaskEntry.AMOUNT_OPTIONS + ", " +
                ToDoContract.TaskEntry.KEY_PRICE + " " + ToDoContract.TaskEntry.PRICE_OPTIONS + ", " +
                ToDoContract.TaskEntry.KEY_COMPLETED + " " + ToDoContract.TaskEntry.COMPLETED_OPTIONS +
                ");"
        private val DROP_TODO_TABLE = "DROP TABLE IF EXISTS " + ToDoContract.TaskEntry.TABLE_NAME
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DB_CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {
        db?.execSQL(DROP_TODO_TABLE)
        onCreate(db)
    }
}
