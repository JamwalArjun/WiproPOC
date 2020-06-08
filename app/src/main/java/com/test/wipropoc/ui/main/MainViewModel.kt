package com.test.wipropoc.ui.main

import androidx.lifecycle.ViewModel
import com.test.pocwipro.modal.ApiResponse
import com.test.pocwipro.network.APIFactory
import com.test.wipropoc.R
import com.test.wipropoc.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

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

        }

        override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
            Logger.v(R.string.response,response?.body().toString())
        }
    }
}