package com.test.wipropoc.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.test.wipropoc.R
import com.test.wipropoc.model.ApiResponse
import com.test.wipropoc.model.Row
import com.test.wipropoc.model.ScreenState
import com.test.wipropoc.network.APIFactory
import com.test.wipropoc.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataTitle = MutableLiveData<String>()
    val screenStateLiveData = MutableLiveData<ScreenState>()
    private val screenState: ScreenState = ScreenState()
    private lateinit var rowList: MutableList<Row>

    /*
    * This method to fetch data from API
    */
    fun getData() {
        val data = APIFactory.getApi().fetchAPIAsync()
        data.enqueue(callback)
    }
    private val callback = object : Callback<ApiResponse> {
        override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {
            Logger.e(R.string.error,t.toString())
            screenState.run {
                this.error = application.resources.getString(R.string.error_message)
            }
            screenStateLiveData.postValue(screenState)
        }

        override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
            Logger.v(R.string.response,response?.body().toString())
            response?.isSuccessful.let {
                val resultList = response?.body()?.rows ?: emptyList()
                if (resultList.isEmpty()) {
                    screenState.run {
                        this.error = application.resources.getString(R.string.no_data_available)
                    }
                } else {
                    rowList = resultList.toMutableList()
                    screenState.run {
                        this.rows = resultList.toMutableList()
                    }
                }
                screenStateLiveData.postValue(screenState)
                val title = response?.body()?.title
                liveDataTitle.postValue(title)
            }

        }

    }
}