package com.appveg.farmfamily.ui.users

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.login.LoginActivity
import com.appveg.farmfamily.ui.login.User

class UsersAdapter(private var activity: FragmentActivity?, private var items: ArrayList<User>) :  BaseAdapter() {

    private lateinit var database: Database

    private class ViewHolder(row: View?) {
        var fullName: TextView
        var email: TextView
        var userStatusChecked: CheckBox

        init {
            this.fullName = row?.findViewById(R.id.view_user_full_name) as TextView
            this.email = row?.findViewById(R.id.view_users_email) as TextView
            this.userStatusChecked = row?.findViewById(R.id.user_checked) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_users, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var user = items[position]
        viewHolder.fullName.text = user.fullName
        viewHolder.email.text = user.email
        viewHolder.userStatusChecked.setOnClickListener {
            var userStatus = viewHolder.userStatusChecked.isChecked
            updateStatus(userStatus, user.id!!)
            viewHolder.userStatusChecked.isChecked = userStatus
            checkLogOut(user)
        }
        viewHolder.userStatusChecked.isChecked = checked(user.status)

        return view
    }

    override fun getItem(i: Int): User {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    // checked load default (case broken)
    private fun checked(status: Int): Boolean {
        var result: Boolean = false
        if (3 == status) {
            result = true
        }
        return result
    }

    private fun updateStatus(checked: Boolean, id: Int) : Int {
        database = Database(activity)
        var user: User = User()
        if (checked) {
            user.id = id
            user.status = 3
        } else {
            user.id = id
            user.status = 0
        }
        return database.updateStatusById(user)
    }

    private fun checkLogOut(user : User){
        if(user.status != 0 && user.status != 3){
            val accountsIntent = Intent(activity, LoginActivity::class.java)
            activity!!.startActivity(accountsIntent)
        }
    }
}
