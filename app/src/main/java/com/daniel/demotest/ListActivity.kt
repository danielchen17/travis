package com.daniel.demotest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.listitem.view.*

class ListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListAdapter(this)
    }
}

data class User(val id: String, val name: String, val level: Int)

class DataManager {
    fun getUserList(): List<User> {
        return listOf(User("001", "Daniel", 10),
            User("002", "John", 9),
            User("003", "Johnny", 8),
            User("004", "Jack", 7),
            User("005", "Jackson", 6),
            User("006", "Dan", 5),
            User("007", "Barry", 4),
            User("008", "Ron", 3),
            User("009", "Adam", 2))
    }
}

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var list: List<User> = DataManager().getUserList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val user = list[position]
            userId.text = user.id
            userName.text = user.name
            setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(Constant.TAG_LEVEL, user.level)
                    putExtra(Constant.TAG_NAME, user.name)

                }
                context.startActivity(intent)
            }
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

