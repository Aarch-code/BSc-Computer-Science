// Prachi Tandel : 2023ebcs178
package com.example.simplepersonalorganizer_2023ebcs178

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PersonalOrganizer.db"
        private const val DATABASE_VERSION = 1

        // Table name
        private const val TABLE_USERS = "Users"

        // Column names
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert user data into the database
    fun insertUser(name: String, email: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
        }

        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L
    }

    // Retrieve all users with name "admin"
    fun getAdminUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_NAME = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf("admin"))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                userList.add(User(id, name, email))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }
}

// Data class to represent a User
data class User(
    val id: Int,
    val name: String,
    val email: String
)
