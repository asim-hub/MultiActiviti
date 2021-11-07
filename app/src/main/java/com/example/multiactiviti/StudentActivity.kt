package com.example.multiactiviti

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentActivity : AppCompatActivity() {
    private var mBtGoBack: Button? = null
    private lateinit var edName: EditText
    private lateinit var edGroup: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:StudentAdapter? = null
    private var stud:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        mBtGoBack = findViewById(R.id.bt_go_back) as Button?
        mBtGoBack!!.setOnClickListener { finish() }

        initView()
        initRecyclerView()

        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addStudent() }
        btnView.setOnClickListener{ getStudents() }
        btnUpdate.setOnClickListener{ updateStudent() }


        adapter?.setOnClickItem {
            Toast.makeText(this, it.name_stud, Toast.LENGTH_SHORT).show()
            edName.setText(it.name_stud)
            edGroup.setText(it.group_stud)
            stud = it

        }
        adapter?.setOnClickDeleteItem {
            deleteStudent(it.idstud)
        }
    }

    private fun getStudents() {
        val studList = sqLiteHelper.getAllStud()
        Log.e("pppp", "${studList.size}")

        adapter?.addItems(studList)
    }

    private fun addStudent() {
        val name_stud = edName.text.toString()
        val group_stud = edGroup.text.toString()
        if(name_stud.isEmpty() || group_stud.isEmpty()) {
            Toast.makeText(this, "Please enter requried field", Toast.LENGTH_SHORT).show()
        } else {
            val stud = StudentModel(name_stud = name_stud, group_stud = group_stud)
            val status = sqLiteHelper.insertStudent(stud)
            if(status > -1) {
                Toast.makeText(this, "Student added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else {
                Toast.makeText(this, "Record not saved.", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private  fun updateStudent() {
        val name_stud = edName.text.toString()
        val group_stud = edGroup.text.toString()

        if (name_stud == stud?.name_stud && group_stud == stud?.group_stud) {
            Toast.makeText(this, "Record no change...", Toast.LENGTH_SHORT).show()
            return
        }

        if(stud == null) return

        val stud = StudentModel(idstud = stud!!.idstud, name_stud = name_stud, group_stud = group_stud)
        val status = sqLiteHelper.updateStudent(stud)
        if(status > -1) {
            clearEditText()
            getStudents()
        } else {
            Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(idstud:Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you what to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _->
            dialog.dismiss()
            sqLiteHelper.deleteStudById(idstud)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {
                dialog, _->dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edGroup.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView (){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edGroup = findViewById(R.id.edGroup)
        btnAdd = findViewById(R.id.btnAddStud)
        btnView = findViewById(R.id.btnViewStud)
        btnUpdate = findViewById(R.id.btnUpdateStud)
        recyclerView = findViewById(R.id.recyclerViewStud)
    }
}