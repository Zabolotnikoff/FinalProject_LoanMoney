package com.focusstart.android.finalproject.loanmoneyonline.di.presenters

import com.focusstart.android.finalproject.loanmoneyonline.domain.usecase.*
import com.focusstart.android.finalproject.loanmoneyonline.presentation.authentication.AuthenticationPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.authentication.IAuthenticationPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegisterLoan.ExplanationAfterRegisterLoanPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegisterLoan.IExplanationAfterRegisterLoanPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegistration.ExplanationAfterRegistrationPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegistration.IExplanationAfterRegistrationPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.listOfLoans.IListOfLoansPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.listOfLoans.ListOfLoansPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.loanProfile.ILoanProfilePresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.loanProfile.LoanProfilePresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.registrationLoan.ILoanRegistrationPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.registrationLoan.LoanRegistrationPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.registrationUser.IRegistrationPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.registrationUser.RegistrationPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.splashScreen.ISplashScreenPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.splashScreen.SplashScreenPresenterImpl
import com.focusstart.android.finalproject.loanmoneyonline.presentation.startWindow.IStartWindowPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.startWindow.StartWindowPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [UseCasesModule::class])
class PresentersModule {
    @Provides
    fun provideAuthenticationPresenter(
        authenticationInAppUseCase: AuthenticationUseCase,
        saveBearerTokenInPreferencesUseCase: SaveBearerTokenUseCase
    ): IAuthenticationPresenter = AuthenticationPresenterImpl(
        authenticationInAppUseCase,
        saveBearerTokenInPreferencesUseCase
    )

    @Provides
    fun provideExplanationAfterRegisterLoanPresenter(): IExplanationAfterRegisterLoanPresenter =
        ExplanationAfterRegisterLoanPresenterImpl()

    @Provides
    fun provideExplanationAfterRegistrationPresenter(): IExplanationAfterRegistrationPresenter =
        ExplanationAfterRegistrationPresenterImpl()

    @Provides
    fun provideListOfLoansPresenter(
        getListOfLoansUseCase: GetListOfLoansUseCase
    ): IListOfLoansPresenter = ListOfLoansPresenterImpl(
        getListOfLoansUseCase
    )

    @Provides
    fun provideLoanProfilePresenter(): ILoanProfilePresenter = LoanProfilePresenterImpl()

    @Provides
    fun provideLoanRegistrationPresenter(
        loanRegistrationUseCase: LoanRegistrationUseCase,
        getConditionsLoanUseCase: GetConditionsLoanUseCase
    ): ILoanRegistrationPresenter = LoanRegistrationPresenterImpl(
        loanRegistrationUseCase,
        getConditionsLoanUseCase
    )

    @Provides
    fun provideRegistrationPresenter(
        registrationInAppUseCase: RegistrationInAppUseCase
    ): IRegistrationPresenter = RegistrationPresenterImpl(
        registrationInAppUseCase
    )

    @Provides
    fun provideSplashScreenPresenter(
        checkingBearerTokenAvailabilityUseCase: CheckingBearerTokenAvailabilityUseCase
    ): ISplashScreenPresenter = SplashScreenPresenterImpl(
        checkingBearerTokenAvailabilityUseCase
    )

    @Provides
    fun provideStartWindowPresenter(): IStartWindowPresenter = StartWindowPresenterImpl()
}