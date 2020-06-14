package com.test.wipropoc


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.test.wipropoc.ui.activities.MainActivity
import com.test.wipropoc.ui.fragment.MainFragment
import com.test.wipropoc.ui.adapter.RowRecyclerViewAdapter
import junit.framework.Assert.assertFalse

import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.not

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentTest {
    @get :Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /*
    * Test case to check recycler view  visible
    */
    @Test
    fun test_isListFragmentVisible(){

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.tvError)).check(matches(not(isDisplayed())))
    }

    /*
   * Test case to scroll the recycler view till last element
   */
    @Test
    fun test_ScrollToLastPosition(){
      onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RowRecyclerViewAdapter.ViewHolder>(12))
    }

    /*
    * Test case when we get all data from api  recycler view is displayed
    */
    @Test
    fun test_AllDataShowingInList() {

        activityRule.scenario.onActivity { activity ->
            val fragment: MainFragment = activity.supportFragmentManager.fragments[0] as MainFragment
            fragment.mainViewModel.screenStateLive.observeOnce {
                assertTrue(it.error == "")
                assertTrue(it.rows.isNotEmpty())
            }
        }
    }


    /*
    * Test case when there is error from API.
    */
    @Test
    fun test_ErrorFromApi() {
        activityRule.scenario.onActivity { activity ->
            val fragment: MainFragment = activity.supportFragmentManager.fragments[0] as MainFragment
            fragment.mainViewModel.screenStateLive.observeOnce {
                assertFalse(it.error == fragment.activity?.getString(R.string.error_message))
            }
        }
    }

    /**
     *  Extension method on LiveData over ObserveForever
     */
    private fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
        observeForever(object: Observer<T> {
            override fun onChanged(value: T) {
                removeObserver(this)
                observer(value)
            }
        })
    }
}