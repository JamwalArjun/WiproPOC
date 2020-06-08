package com.test.pocwipro.network


import com.test.pocwipro.modal.ApiResponse
import retrofit2.Call
import retrofit2.http.GET


interface ServiceApi {
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun fetchAPIAsync() : Call<ApiResponse>
}