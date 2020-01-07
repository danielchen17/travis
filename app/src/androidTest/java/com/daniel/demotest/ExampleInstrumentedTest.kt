package com.daniel.demotest

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Checks
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputEditText
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Thread.sleep

class ExampleInstrumentedTest {
    private lateinit var server : MockWebServer
    private val mockedResponse = "I am mocked response"

    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun launchActivity(startIntent: Intent?): MainActivity {

//            server = MockWebServer()
//            server.enqueue(MockResponse().apply {
//                setResponseCode(200)
//                addHeader("Content-Type", "application/json;charset=utf-8")
//                addHeader("Cache-Control", "no-cache")
//                setBody(mockedResponse)
//            })
//
//            val url = server.url("").toString()
//
//            mockkObject(Constant)
//            every { Constant.url }.returns(url)

            return super.launchActivity(startIntent)
        }
    }


    fun notestMockServer() {
        onView(withId(R.id.textView)).check(matches(withText("false")))
    }

    fun notestAsynchronous() {
        IdlingRegistry.getInstance().register(Idling.idlingResource)
        onView(withId(R.id.textView)).check(matches(withText("false")))
        IdlingRegistry.getInstance().unregister(Idling.idlingResource)

    }

    class CustomViewAction(private val text: String) : ViewAction {
        override fun getDescription(): String {
            return "CustomViewAction applied $text"
        }

        override fun getConstraints(): Matcher<View> {
            return isDisplayed()
        }

        override fun perform(uiController: UiController?, view: View?) {

            val editText = view!!.findViewById<EditText>(R.id.editText)
            val save = view.findViewById<Button>(R.id.save)
            editText.text.clear()
            editText.text.append(text)
            save.callOnClick()
        }
    }

    private fun customTextViewMatcher(text: String): BoundedMatcher<View, CustomWidget> {
        Checks.checkNotNull(text)
        return object : BoundedMatcher<View, CustomWidget>(CustomWidget::class.java) {
            override fun describeTo(description: Description?) {
            }

            override fun matchesSafely(view: CustomWidget?): Boolean {
                val textView = view?.findViewById<TextView>(R.id.textView)
                return text == textView?.text.toString()
            }
        }
    }

    @Test
    fun testDisplayUser() {
        Intents.init()
        onView(withId(R.id.button)).perform(click())

        intended(hasComponent(ListActivity::class.java.name))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(hasComponent(DetailActivity::class.java.name))
        onView(withText("Daniel")).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("level is 10")))

        onView(withHint("nickname")).perform(typeText("God"))
        onView(withText("Click")).perform(click())
        onView(withText("God is level 10")).check(matches(isDisplayed()))

        Intents.release()
    }


    @After
    fun tearDown() {
       // server.shutdown()

    }
}




//    @get:Rule
//    val activityTestRule : ActivityTestRule<MainActivity> =
//        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
//            override fun getActivityIntent(): Intent {
//                val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//                return Intent(appContext, MainActivity::class.java)
//            }
//        }