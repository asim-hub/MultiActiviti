package com.example.multiactiviti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

class UserActivity : AppCompatActivity() {
    private var mBtGoBack: Button? = null
    private lateinit var edEmail: EditText
    private lateinit var edRole: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:UserAdapter? = null
    private var usr:UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mBtGoBack = findViewById(R.id.bt_go_back) as Button?
        mBtGoBack!!.setOnClickListener { finish() }

        initView()
        initRecyclerView()

        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addUser() }
        btnView.setOnClickListener{ getUsers() }
        btnUpdate.setOnClickListener{ updateUser() }


        adapter?.setOnClickItem {
            Toast.makeText(this, it.email, Toast.LENGTH_SHORT).show()
            edEmail.setText(it.email)
            edRole.setText(it.role)
            usr = it

        }
        adapter?.setOnClickDeleteItem {
            deleteUser(it.iduser)
        }
    }

    private fun getUsers() {
        val usrList = sqLiteHelper.getAllUsers()
        Log.e("pppp", "${usrList.size}")

        adapter?.addItems(usrList)
    }

    private fun addUser() {
        val email = edEmail.text.toString()
        val role = edRole.text.toString()
        if(email.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please enter requried field", Toast.LENGTH_SHORT).show()
        } else {
            val std = UserModel(email = email, role = role)
            val status = sqLiteHelper.insertUser(std)
            if(status > -1) {
                Toast.makeText(this, "User added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getUsers()
            } else {
                Toast.makeText(this, "Record not saved.", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private  fun updateUser() {
        val email = edEmail.text.toString()
        val role = edRole.text.toString()

        if (email == usr?.email && role == usr?.role) {
            Toast.makeText(this, "Record no change...", Toast.LENGTH_SHORT).show()
            return
        }

        if(usr == null) return

        val usr = UserModel(iduser = usr!!.iduser, email = email, role = role)
        val status = sqLiteHelper.updateUser(usr)
        if(status > -1) {
            clearEditText()
            getUsers()
        } else {
            Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteUser(iduser:Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you what to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _->
            dialog.dismiss()
            sqLiteHelper.deleteUserById(iduser)
            getUsers()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {
                dialog, _->dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edEmail.setText("")
        edRole.setText("")
        edEmail.requestFocus()
    }

    private fun initRecyclerView (){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edEmail = findViewById(R.id.edEmail)
        edRole = findViewById(R.id.edRole)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }

}