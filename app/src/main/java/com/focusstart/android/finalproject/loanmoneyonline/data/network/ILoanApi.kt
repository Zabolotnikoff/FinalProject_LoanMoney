package com.focusstart.android.finalproject.loanmoneyonline.data.network

import com.focusstart.android.finalproject.loanmoneyonline.data.model.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ILoanApi {
    @POST("login")
    fun loginIntoApp(
        @Body bodyRequest: Auth
    ): Single<ResponseBody>

    @POST("registration")
    fun registrationInApp(
        @Body bodyRequest: Auth
    ): Single<UserEntity>

    @GET("loans/")
    fun getLoansList(
        @Header("Authorization") authHeader: String
    ): Single<List<Loan>>

    @POST("loans/")
    fun createNewLoan(
        @Body bodyRequest: LoanRequest,
        @Header("Authorization") authHeader: String
    ): Single<Loan>

    @GET("loans/{id}")
    fun getLoanData(
        @Header("Authorization") authHeader: String
      //id
    ): Single<Loan>

    @GET("loans/conditions")
    fun getLoanConditions(
        @Header("Authorization") authHeader: String
    ): Single<LoanConditions>

}