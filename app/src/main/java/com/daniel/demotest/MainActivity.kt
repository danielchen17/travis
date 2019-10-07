package com.daniel.demotest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.android.synthetic.main.activity_main.*

import java.io.IOException
import okhttp3.OkHttpClient

import okhttp3.Request




//Model
data class UserInfo(var firstName: String, var lastName: String)

//View
interface IView {
    fun receivedUserName(name: String)
}

class Server {
    fun requestUserName(): UserInfo {
        return UserInfo("Daniel", "Chen")
    }
}

//Presenter
class MainActivityPresenter(private val view: IView, private val server: Server) {

    fun requestUserName() {
        val user = server.requestUserName()
        view.receivedUserName("${user.firstName} ${user.lastName}")
    }
}

object Constant {
    val url = "http://dummy.restapiexample.com/api/v1/employee/1"
    const val TAG_LEVEL = "Level"
    const val TAG_NAME = "Name"
}

object Idling {
    val idlingResource = CountingIdlingResource("test")
}

class ServerHelper() {
    fun request(callback: (response: String) -> Unit) {
        Idling.idlingResource.increment()
        HandlerThread("demo").apply {
            start()
            Handler(looper).post {
                val client = OkHttpClient()
                val request  = Request.Builder()
                    .url(Constant.url)
                    .build()
                try {
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string()
                    responseString?.let {
                        Handler(Looper.getMainLooper()).post {
                            callback(it)
                        }
                    }
                    Idling.idlingResource.decrement()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
//GlobalScope.launch(Dispatchers.Default) {
//
//
//}


class MainActivity : AppCompatActivity(), IView {


    private var presenter = MainActivityPresenter(this, Server())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ServerHelper().request {
            textView.text = it
        }
    }

    override fun receivedUserName(name: String) {
        textView.text = name
    }


}

data class webserver(val ip: String)

class ServerManager {


    fun getUserListFromJava(): List<String> {
        return JavaServer.getInstance().requestUserNameList()
    }

    fun getUserListFromKotlin(): List<String> {
        return KotlinServer.instance.requestUserNameList()
    }
}

interface DataReceivedListener {
    fun onReceived(data: UserData)
}

data class UserData(val id: String, val displayName: String)

class RequestManager {
    fun requestDisplayNameByListener(id: String, listener: DataReceivedListener) {
        //server calls
        listener.onReceived(UserData("1", "Daniel Chen"))
    }

    fun requestDisplayNameByLambda(id: String, lambda: (data: UserData) -> Unit) {
        //server calls
        lambda(UserData("1", "Daniel Chen"))
    }
}

class DemoPresenter(private val requestManager: RequestManager, val view: IView) {

    fun getDisplayNameByListener() {
        requestManager.requestDisplayNameByListener("1", object : DataReceivedListener {
            override fun onReceived(data: UserData) {
                view.receivedUserName(data.displayName)
            }
        })
    }

    fun getDisplayNameByLambda() {
        requestManager.requestDisplayNameByLambda("1") { data ->
            view.receivedUserName(data.displayName)
        }
    }


}

class Util {
    private fun getRate(): Int {
        return 5
    }

    fun calculate(value: Int): Int {
        return getRate() * value
    }
}

class KotlinServer {
    companion object {
        val instance by lazy {
            KotlinServer()
        }
    }

    fun requestUserNameList(): List<String> {
        return listOf("User A", "User B")
    }
}