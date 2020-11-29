package com.focusstart.android.finalproject.loanmoneyonline.presentation.registrationUser

import android.os.Bundle
import android.util.Log
import com.focusstart.android.finalproject.loanmoneyonline.Constants.BUNDLE_KEY_REGISTRATION_NAME
import com.focusstart.android.finalproject.loanmoneyonline.Constants.BUNDLE_KEY_REGISTRATION_PASSWORD
import com.focusstart.android.finalproject.loanmoneyonline.Constants.CODE_BAD_REQUEST
import com.focusstart.android.finalproject.loanmoneyonline.Constants.TAG_ERROR
import com.focusstart.android.finalproject.loanmoneyonline.data.model.UserEntity
import com.focusstart.android.finalproject.loanmoneyonline.domain.usecase.RegistrationInAppUseCase
import com.focusstart.android.finalproject.loanmoneyonline.presentation.common.applySchedulers
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response

class RegistrationPresenterImpl(
        private val registrationInAppUseCase: RegistrationInAppUseCase) :
        IRegistrationPresenter {

    companion object {
        private const val MESSAGE_EMPTY_FIELDS = "Заполните все поля"
        private const val MESSAGE_USER_EXISTS = "Пользователь с таким именем уже существует"
    }

    private var view: IRegistrationView? = null
    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: IRegistrationView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun onRegistrationButtonClicked(username: String, password: String) {
        if (validationOfEnteredValues(username, password))
            registrationInApp(username, password)
        else view?.showToast(MESSAGE_EMPTY_FIELDS)
    }

    private fun validationOfEnteredValues(username: String, password: String): Boolean = username.isNotEmpty() && password.isNotEmpty()

    private fun registrationInApp(username: String, password: String) {
        registrationInAppUseCase(username, password)
                .compose(applySchedulers())
                .subscribe(object : SingleObserver<Response<UserEntity>> {
                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onSuccess(response: Response<UserEntity>) {
                        processResponseRegistration(response, username, password)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG_ERROR, "registration in App: ${e.message}")
                    }
                })
    }

    private fun processResponseRegistration(response: Response<UserEntity>, username: String, password: String) {
        if (response.isSuccessful) {
            automaticallyAuthentication(username, password)
        } else {
            when (response.code()) {
                CODE_BAD_REQUEST -> view?.showUserNameError(MESSAGE_USER_EXISTS)
            }
        }
    }

    private fun automaticallyAuthentication(username: String, password: String) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_KEY_REGISTRATION_NAME, username)
        bundle.putString(BUNDLE_KEY_REGISTRATION_PASSWORD, password)
        view?.navigateToAuthenticationFragment(bundle)
    }

}