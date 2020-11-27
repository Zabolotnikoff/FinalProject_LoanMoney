package com.focusstart.android.finalproject.loanmoneyonline.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.focusstart.android.finalproject.loanmoneyonline.R
import com.focusstart.android.finalproject.loanmoneyonline.di.ExplanationAfterRegisterLoanPresenterFactory
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegisterLoan.IExplanationAfterRegisterLoanPresenter
import com.focusstart.android.finalproject.loanmoneyonline.presentation.explanationAfterRegisterLoan.IExplanationAfterRegisterLoanView

class ExplanationAfterRegisterLoanFragment : Fragment(), IExplanationAfterRegisterLoanView {
    private var presenter: IExplanationAfterRegisterLoanPresenter? = null
    private lateinit var btnNavigateToListOfLoans: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_explanation_after_register_loan, container, false)
        initPresenter()
        initView(fragmentLayout)
        return fragmentLayout
    }

    private fun initView(fragmentLayout: View) {
        btnNavigateToListOfLoans = fragmentLayout.findViewById(R.id.btn_navigate_in_loans_list)
        btnNavigateToListOfLoans.setOnClickListener { presenter?.onNavigateToListOfLoansButtonClicked() }
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    private fun initPresenter() {
        presenter = ExplanationAfterRegisterLoanPresenterFactory.create()
        presenter?.attachView(this)
    }

    override fun navigateToListOfLoansFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_explanationAfterRegisterLoanFragment_to_listOfLoansFragment)
    }

}