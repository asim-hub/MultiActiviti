package com.example.multiactiviti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var usrList: ArrayList<UserModel> = ArrayList()
    private var onClickItem: ((UserModel)->Unit)? = null
    private var onClickDeleteItem: ((UserModel)->Unit)? = null

    fun addItems(items: ArrayList<UserModel>) {
        this.usrList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (UserModel)->Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (UserModel)->Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_usr, parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val usr = usrList[position]
        holder.bindView(usr)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(usr)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(usr) }
    }

    override fun getItemCount(): Int {
        return usrList.size
    }

    class UserViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var iduser = view.findViewById<TextView>(R.id.tvIdUser)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var role = view.findViewById<TextView>(R.id.tvRole)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(usr: UserModel) {
            iduser.text = usr.iduser.toString()
            email.text = usr.email
            role.text = usr.role
        }

    }
}