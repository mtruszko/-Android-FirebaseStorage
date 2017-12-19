package com.example.maro.productlistproj1

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class ToDoProvider : ContentProvider() {
    companion object {
        val TASK = 100
        val TASK_WITH_ID = 101

        fun createUriMatcher(): UriMatcher {
            var matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            val authority = ToDoContract.CONTENT_AUTHORITY

            matcher.addURI(authority, ToDoContract.TASK_PATH, TASK)
            matcher.addURI(authority, "${ToDoContract.TASK_PATH}/#", TASK_WITH_ID)

            return matcher
        }

        val sUriMatcher: UriMatcher = createUriMatcher()
        var mOpenHelper: SQLiteOpenHelper? = null
    }

    override fun onCreate(): Boolean {
        mOpenHelper = ToDoDbHelper(context)
        return true
    }

    override fun query(uri: Uri?, projection: Array<out String>?,
                       selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor? {
        val db: SQLiteDatabase = mOpenHelper?.readableDatabase as SQLiteDatabase
        val match: Int = sUriMatcher.match(uri)
        var cursor: Cursor?

        when (match) {
            TASK -> {
                cursor = db.query(ToDoContract.TaskEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder)
            }
            TASK_WITH_ID -> {
                val id: Long = ToDoContract.TaskEntry.getIdFromUri(uri as Uri)
                cursor = db.query(ToDoContract.TaskEntry.TABLE_NAME, projection,
                        "${ToDoContract.TaskEntry.KEY_ID} = ?", arrayOf(id.toString()), null, null, sortOrder)
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        cursor?.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri?): String? {
        val match: Int = sUriMatcher.match(uri)

        when (match) {
            TASK_WITH_ID -> return ToDoContract.TaskEntry.CONTENT_ITEM_TYPE
            TASK -> return ToDoContract.TaskEntry.CONTENT_TYPE
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val db = mOpenHelper?.writableDatabase
        val match: Int = sUriMatcher.match(uri)
        var insertionUri: Uri?
        var insertedId: Long

        when (match) {
            TASK -> {
                insertedId = db!!.insert(ToDoContract.TaskEntry.TABLE_NAME, null, values)

                insertionUri = if (insertedId > 0) {
                    ToDoContract.TaskEntry.buildWithId(insertedId)
                } else {
                    throw SQLException("Failed to insert row into $uri")
                }
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        context.contentResolver.notifyChange(uri, null)
        return insertionUri
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = mOpenHelper?.writableDatabase
        val match = sUriMatcher.match(uri)
        var deleted: Int

        var customSelection = selection ?: "1"

        when (match) {
            TASK -> deleted = db!!.delete(ToDoContract.TaskEntry.TABLE_NAME, customSelection, selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        if (deleted > 0) {
            context.contentResolver.notifyChange(uri, null)
        }

        return deleted
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?,
                        selectionArgs: Array<out String>?): Int {
        val db = mOpenHelper?.writableDatabase
        val match = sUriMatcher.match(uri)
        var updated: Int

        when (match) {
            TASK -> updated = db!!.update(ToDoContract.TaskEntry.TABLE_NAME, values, selection, selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        if (updated > 0) {
            context.contentResolver.notifyChange(uri, null)
        }
        return updated
    }
}