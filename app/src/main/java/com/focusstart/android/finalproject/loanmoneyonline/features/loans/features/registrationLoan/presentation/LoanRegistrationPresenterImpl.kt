package com.focusstart.android.finalproject.loanmoneyonline.features.loans.features.registrationLoan.presentation

import android.os.Bundle
import android.util.Log
import com.focusstart.android.finalproject.loanmoneyonline.utils.Constants
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.model.Loan
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.data.model.LoanConditions
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.features.registrationLoan.domain.usecase.GetConditionsLoanUseCase
import com.focusstart.android.finalproject.loanmoneyonline.features.loans.features.registrationLoan.domain.usecase.LoanRegistrationUseCase
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response

class LoanRegistrationPresenterImpl(
        private val loanRegistrationUseCase: LoanRegistrationUseCase,
        private val getConditionsLoanUseCase: GetConditionsLoanUseCase
) :
        ILoanRegistrationPresenter {

    companion object {
        private const val STEP_VALUE_IN_SEEK_BAR = 1000
        private const val MIN_VALUE_IN_SEEK_BAR = 1000
        private const val MESSAGE_EMPTY_FIELDS = "Заполните все поля"

        private const val BUNDLE_KEY_PROGRESS = "key_progress"
        private const val BUNDLE_KEY_PERCENT = "key_percent"
        private const val BUNDLE_KEY_PERIOD = "key_period"
        private const val BUNDLE_KEY_MAX_AMOUNT = "key_max_amount"
    }

    private var view: ILoanRegistrationView? = null
    private val compositeDisposable = CompositeDisposable()

    private var progressSeekBar = 0
    private var percent: Double? = null
    private var period: Int? = null
    private var maxAmount: Int? = null

    override fun detachView() {
        this.view = null
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun onResume() {
        if (period == null && percent == null && maxAmount == null) {
            getConditionsLoan()
        }
    }

    private fun getConditionsLoan() {
        getConditionsLoanUseCase()
                .compose(applySchedulers())
                .subscribe(object : SingleObserver<Response<LoanConditions>> {
                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onSuccess(response: Response<LoanConditions>) {
                        processingResponseGettingConditionsLoan(response)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(Constants.TAG_ERROR, "get conditions loan: ${e.message}")
                    }
                })
    }

    private fun processingResponseGettingConditionsLoan(response: Response<LoanConditions>) {
        if (response.isSuccessful) {
            val loanCondition = response.body()
            loanCondition?.let {
                percent = it.percent
                period = it.period
                maxAmount = it.maxAmount
                view?.showImmutableConditions(it.percent, it.period)
                setMaxValueInSeekBar(it.maxAmount)
            }
        }
    }

    private fun setMaxValueInSeekBar(maxAmount: Int) = view?.setMaxInSeekBar(calculateValueSeekBar(maxAmount))

    private fun calculateValueSeekBar(maxAmount: Int) = (maxAmount - MIN_VALUE_IN_SEEK_BAR) / STEP_VALUE_IN_SEEK_BAR

    override fun onRegistrationLoanButtonClicked(
            firstName: String,
            secondName: String,
            phoneNumber: String,
            amount: String,
            period: String,
            percent: String
    ) {
        if (validationOfEnteredValues(firstName, secondName, phoneNumber, amount))
            registrationLoan(firstName, secondName, phoneNumber, amount, period, percent)
        else view?.showToast(MESSAGE_EMPTY_FIELDS)
    }

    private fun validationOfEnteredValues(
            firstName: String,
            secondName: String,
            phoneNumber: String,
            amount: String
    ): Boolean =
            firstName.isNotEmpty() && secondName.isNotEmpty() && phoneNumber.isNotEmpty() && amount.isNotEmpty()

    override fun getTransformedProgressValueInSeekBar(progress: Int): Int {
        progressSeekBar = progress
        return MIN_VALUE_IN_SEEK_BAR + (progress * STEP_VALUE_IN_SEEK_BAR)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BUNDLE_KEY_PROGRESS, progressSeekBar)
        period?.let { outState.putInt(BUNDLE_KEY_PERIOD, it) }
        percent?.let { outState.putDouble(BUNDLE_KEY_PERCENT, it) }
        maxAmount?.let { outState.putInt(BUNDLE_KEY_MAX_AMOUNT, it) }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        progressSeekBar = savedInstanceState.getInt(BUNDLE_KEY_PROGRESS)
        period = savedInstanceState.getInt(BUNDLE_KEY_PERIOD)
        percent = savedInstanceState.getDouble(BUNDLE_KEY_PERCENT)
        maxAmount = savedInstanceState.getInt(BUNDLE_KEY_MAX_AMOUNT)

        restoreViewSeekBar(maxAmount!!, progressSeekBar)
        view?.showImmutableConditions(percent!!, period!!)
    }

    private fun restoreViewSeekBar(maxAmount: Int, progressSeekBar: Int) {
        setMaxValueInSeekBar(maxAmount)
        view?.setProgressInSeekBar(progressSeekBar)
    }

    override fun <T> attachView(view: T) {
        this.view = view as ILoanRegistrationView
    }

    private fun registrationLoan(
            firstName: String,
            secondName: String,
            phoneNumber: String,
            amount: String,
            period: String,
            percent: String
    ) {
        loanRegistrationUseCase(firstName, secondName, phoneNumber, amount, period, percent)
                .compose(applySchedulers())
                .subscribe(object : SingleObserver<Response<Loan>> {
                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onSuccess(response: Response<Loan>) {
                        processingResponseRegistrationLoan(response)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(Constants.TAG_ERROR, "registration loan: ${e.message}")
                    }
                })
    }

    private fun processingResponseRegistrationLoan(response: Response<Loan>) {
        if (response.isSuccessful) {
            val loan = response.body()
            loan?.let { view?.navigateToExplanationAfterRegisterLoanFragment() }
        }
    }

}