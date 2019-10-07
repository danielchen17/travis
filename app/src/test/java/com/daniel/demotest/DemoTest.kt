package com.daniel.demotest

import cucumber.api.java8.En
import com.daniel.demotest.DemoTest
import cucumber.api.java.en.Given
import cucumber.api.java.en.But
import io.mockk.*


class DemoTest : En {

    init {
        val view = mockk<IView>()
        val server = mockk<Server>()

        Given("username from server") {
            every {
                view.receivedUserName(any())
            } just Runs

            every {
                server.requestUserName()
            }.returns(UserInfo("Daniel", "Chen"))
        }

        When("request username in main page") {
            var presenter = MainActivityPresenter(view, server)
            presenter.requestUserName()

        }

        Then("received username in main page") {
            verify {
                view.receivedUserName(any())
            }
        }
    }
}