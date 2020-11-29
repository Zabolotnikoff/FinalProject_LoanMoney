package com.focusstart.android.finalproject.loanmoneyonline.data.model

data class LoanRequest(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)