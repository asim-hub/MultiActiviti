package com.example.multiactiviti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var mBtLaunchTableUsers: Button? = null
    private var mBtLaunchTableStudents: Button? = null
    private var mBtLaunchTableSubjects: Button? = null
    private var mBtLaunchTableSlots: Button? = null
    private var mBtLaunchTableTimestamp: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBtLaunchTableUsers = findViewById(R.id.bt_users) as Button?
        mBtLaunchTableStudents = findViewById(R.id.bt_students) as Button?
        mBtLaunchTableSubjects = findViewById(R.id.bt_subjects) as Button?
        mBtLaunchTableSlots = findViewById(R.id.bt_slots) as Button?
        mBtLaunchTableTimestamp = findViewById(R.id.bt_timestamp) as Button?

        mBtLaunchTableUsers!!.setOnClickListener { launchActivity(1) }
        mBtLaunchTableStudents!!.setOnClickListener { launchActivity(2) }
        mBtLaunchTableSubjects!!.setOnClickListener { launchActivity(3) }
        mBtLaunchTableSlots!!.setOnClickListener { launchActivity(4) }
        mBtLaunchTableTimestamp!!.setOnClickListener { launchActivity(5) }
    }
    private fun launchActivity(launch: Int) {
        // aici trebuie sa fac pentru fiecare clasa in parte

        if (launch == 1) {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        if (launch == 2) {
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
        }

    }
}
