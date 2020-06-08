package com.test.wipropoc.model


data class ScreenState(
    var rows: List<Row> = listOf(),
    var error: String = ""
)