package com.example.multiactiviti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var studList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel)->Unit)? = null
    private var onClickDeleteItem: ((StudentModel)->Unit)? = null

    fun addItems(items: ArrayList<StudentModel>) {
        this.studList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel)->Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel)->Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_stud, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val stud = studList[position]
        holder.bindView(stud)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(stud)}
        holder.btnDeleteStud.setOnClickListener{ onClickDeleteItem?.invoke(stud) }
    }

    override fun getItemCount(): Int {
        return studList.size
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var idstud = view.findViewById<TextView>(R.id.tvIdStud)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var group = view.findViewById<TextView>(R.id.tvGroup)
        var btnDeleteStud = view.findViewById<Button>(R.id.btnDeleteStud)

        fun bindView(stud: StudentModel) {
            idstud.text = stud.idstud.toString()
            name.text = stud.name_stud
            group.text = stud.group_stud
        }

    }
}