package com.daniel.demotest

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*


class ServerApi {
    fun requestUser(): List<UserInfo>? {
        //implement remote connection
        return null
    }
}

class MyViewModel : ViewModel() {
    var apiManager: ServerApi? = null

    private val users: MutableLiveData<List<UserInfo>> by lazy {
        MutableLiveData<List<UserInfo>>().also {
            val list = loadUsers()?.filter { it.firstName == "Daniel" }
            it.postValue(list)
        }
    }

    fun getUsers(): LiveData<List<UserInfo>> {
        return users
    }

    private fun loadUsers(): List<UserInfo>? {
        return apiManager?.requestUser()
    }

}

class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        model.apiManager = ServerApi()
        model.getUsers().observe(this, Observer<List<UserInfo>>{ users ->
            // update UI
        })
    }
}

