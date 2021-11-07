package com.example.multiactiviti

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "qrapp.db"
        private const val TBL_USER = "tbl_user"
        private const val TBL_SUBJECT = "tbl_subject"
        private const val TBL_STUDENT = "tbl_student"
        private const val TBL_SLOT = "tbl_slot"
        private const val TBL_TIMESTAMP = "tbl_timestamp"

        private const val IDUSER = "iduser"
        private const val EMAIL = "email"
        private const val ROLE = "role"

        private const val IDSTUD = "idstud"
        private const val NAME_STUD = "name_stud"
        private const val GROUP_STUD = "group_stud"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblUser = ("CREATE TABLE " + TBL_USER + "(" + IDUSER +
                " INTEGER PRIMATY KEY," + EMAIL + " TEXT, " +
                ROLE + " TEXT" + ")" )
        db?.execSQL(createTblUser)

        val createTblStud = ("CREATE TABLE " + TBL_STUDENT + "(" + IDSTUD +
                " INTEGER PRIMATY KEY," + NAME_STUD + " TEXT, " +
                GROUP_STUD + " TEXT" + ")" )
        db?.execSQL(createTblStud)
    }
    @Override
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertUser(usr: UserModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDUSER, usr.iduser)
        contentValues.put(EMAIL, usr.email)
        contentValues.put(ROLE, usr.role)
        val success = db.insert(TBL_USER, null, contentValues)
        db.close()
        return success
    }

    fun insertStudent(stud: StudentModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDSTUD, stud.idstud)
        contentValues.put(NAME_STUD, stud.name_stud)
        contentValues.put(GROUP_STUD, stud.group_stud)
        val success = db.insert(TBL_STUDENT, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllUsers(): ArrayList<UserModel>{
        val usrList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var iduser:Int
        var email:String
        var role:String

        if (cursor.moveToFirst()) {
            do {
                iduser = cursor.getInt(cursor.getColumnIndex("iduser"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                role = cursor.getString(cursor.getColumnIndex("role"))

                val usr = UserModel(iduser = iduser, email = email, role = role)
                usrList.add(usr)
            }while(cursor.moveToNext())
        }

        return usrList
    }

    @SuppressLint("Range")
    fun getAllStud(): ArrayList<StudentModel>{
        val studList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var idstud:Int
        var name_stud:String
        var group_stud:String

        if (cursor.moveToFirst()) {
            do {
                idstud = cursor.getInt(cursor.getColumnIndex("idstud"))
                name_stud = cursor.getString(cursor.getColumnIndex("name_stud"))
                group_stud = cursor.getString(cursor.getColumnIndex("group_stud"))

                val stud = StudentModel(idstud = idstud, name_stud = name_stud, group_stud = group_stud)
                studList.add(stud)
            }while(cursor.moveToNext())
        }

        return studList
    }

    fun updateUser(usr: UserModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDUSER, usr.iduser)
        contentValues.put(EMAIL, usr.email)
        contentValues.put(ROLE, usr.role)

        val success = db.update(TBL_USER, contentValues, "iduser=" +usr.iduser, null )
        db.close()
        return success

    }

    fun updateStudent(stud: StudentModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDUSER, stud.idstud)
        contentValues.put(NAME_STUD, stud.name_stud)
        contentValues.put(GROUP_STUD, stud.group_stud)

        val success = db.update(TBL_STUDENT, contentValues, "idstud=" +stud.idstud, null )
        db.close()
        return success

    }

    fun deleteUserById(iduser: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(IDUSER, iduser)

        val success = db.delete(TBL_USER, "iduser = $iduser", null)
        db.close()
        return success
    }

    fun deleteStudById(idstud: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(IDSTUD, idstud)

        val success = db.delete(TBL_STUDENT, "idstud = $idstud", null)
        db.close()
        return success
    }
}