package com.daniel.demotest

import android.app.Person
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    fun testRequestUserName() {
        val view = mockk<IView>()
        val server = mockk<Server>()
        val presenter = MainActivityPresenter(view, server)

        every {
            view.receivedUserName(any())
        } just Runs

        every {
            server.requestUserName()
        }.returns(UserInfo("Daniel", "Chen"))


        presenter.requestUserName()

        verify {
            view.receivedUserName(any())
        }
    }

//
//
    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()


    fun testMvvmGetUser() {
        //given
        val serverApi = mockk<ServerApi>()
        val viewModel = MyViewModel()
        viewModel.apiManager = serverApi
        val userList = listOf(UserInfo("Daniel", "Chen"),
            UserInfo("Dennis", "Wang"), UserInfo("Dan", "Lee"))
        every { serverApi.requestUser() }.returns(userList)

        //when
        viewModel.getUsers()

        //then
        assertEquals(1, viewModel.getUsers().value?.size)
    }

    //@Test
    fun testStatic() {
        mockkStatic(JavaServer::class)
        mockkObject(KotlinServer)

      //  val javaDatabase = mockk<JavaServer>()
      //  every { JavaServer.getInstance() }.returns(javaDatabase)

      //  val kotlinDatabase = mockk<KotlinServer>()
      //  every { KotlinServer.instance }.returns(kotlinDatabase)

        val expected = listOf("Daniel Chen")

        every { JavaServer.getInstance().requestUserNameList() }.returns(expected)
        every { KotlinServer.instance.requestUserNameList() }.returns(expected)

        val serverManager = ServerManager()
        assertEquals(expected, serverManager.getUserListFromJava())
        assertEquals(expected, serverManager.getUserListFromKotlin())
    }

    fun testListenerAndLambda() {
        val requestManager = mockk<RequestManager>()
        val view = mockk<IView>()
        val presenter = DemoPresenter(requestManager, view)
        val result = UserData("", "")
        val slot = CapturingSlot<DataReceivedListener>()

        every { view.receivedUserName(any()) } just Runs

        every {
            requestManager.requestDisplayNameByListener(any(), capture(slot))
        }.answers {
            slot.captured.onReceived(UserData("dummy", "dummy"))
        }

        presenter.getDisplayNameByListener()

        every {
            requestManager.requestDisplayNameByLambda(any(), captureLambda())
        } .answers {
            lambda<(data: UserData) -> Unit>().invoke(result)
        }

        presenter.getDisplayNameByLambda()

        verify (exactly = 2) {
            view.receivedUserName(any())
        }
    }



    fun testRelaxedMock() {
        val requestManager = mockk<RequestManager>()
        val view = mockk<IView>(relaxed = true)
        val presenter = DemoPresenter(requestManager, view)
        val slot = CapturingSlot<DataReceivedListener>()

        every { view.receivedUserName(any()) } just Runs

        every {
            requestManager.requestDisplayNameByListener(any(), capture(slot))
        }.answers {
            slot.captured.onReceived(UserData("", ""))
        }

        presenter.getDisplayNameByListener()

        verify {
            view.receivedUserName(any())
        }
    }


    fun testSpyPrivateFunction() {
        val spyUtil = spyk<Util>(recordPrivateCalls = true)
        every { spyUtil["getRate"]() }.returns(10)
        val expected = spyUtil.calculate(100)
        assertEquals(1000, expected)
    }



}
