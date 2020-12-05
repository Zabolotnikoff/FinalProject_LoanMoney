package com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.datasource

import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.model.LoanNetwork
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.model.LoanConditionsNetwork
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.model.LoanRequestNetwork
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.network.api.ILoanApi
import io.reactivex.Single
import retrofit2.Response

interface LoanDataSource {
    fun getLoansList(): Single<Response<List<LoanNetwork>>>
    fun registerLoan(loanRequestNetwork: LoanRequestNetwork): Single<Response<LoanNetwork>>
    fun getLoanConditions(): Single<Response<LoanConditionsNetwork>>
}

class LoanDataSourceImpl(private val apiService: ILoanApi) : LoanDataSource {
    override fun getLoansList(): Single<Response<List<LoanNetwork>>> {
        return apiService.getLoansList()
    }

    override fun registerLoan(loanRequestNetwork: LoanRequestNetwork): Single<Response<LoanNetwork>> {
        return apiService.createNewLoan(loanRequestNetwork)
    }

    override fun getLoanConditions(): Single<Response<LoanConditionsNetwork>> {
        return apiService.getLoanConditions()
    }

}