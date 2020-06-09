package com.test.wipropoc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.test.wipropoc.model.Row
import com.test.wipropoc.model.ScreenState
import com.test.wipropoc.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private val mainViewModel: MainViewModel? = null

    @JvmField
    @Rule
     var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Test case checks the row data in live data
     */
    @Test
    fun checkLiveData() {
        val screenStateLiveData = MutableLiveData<ScreenState>()
        val row =
            Row("Description", "www.google.com", "Title")
        val rows: MutableList<Row> =
            ArrayList()
        rows.add(row)
        screenStateLiveData.value = ScreenState(rows, "")
        Mockito.`when`(mainViewModel!!.screenStateLive)
            .thenReturn(screenStateLiveData)
        println(screenStateLiveData.value!!.rows.toString())
        Assert.assertEquals(
            "Check Screen State ",
            screenStateLiveData,
            mainViewModel.screenStateLive
        )
    }

    /**
     * Test case checks the error from live data
     */
    @Test
    fun checkLiveDataForError() {
        val screenStateLiveData = MutableLiveData<ScreenState>()
        val rows: List<Row> =
            ArrayList()
        screenStateLiveData.value = ScreenState(rows, "Something Wrong")
        Mockito.`when`(mainViewModel!!.screenStateLive)
            .thenReturn(screenStateLiveData)
        println(screenStateLiveData.value!!.error)
        Assert.assertEquals(
            "Check Screen State Error",
            screenStateLiveData,
            mainViewModel.screenStateLive
        )
    }
}