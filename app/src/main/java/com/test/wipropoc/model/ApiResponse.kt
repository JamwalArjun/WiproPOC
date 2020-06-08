package com.test.wipropoc.model


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("rows")
    val rows: List<Row>,
    @SerializedName("title")
    val title: String
)