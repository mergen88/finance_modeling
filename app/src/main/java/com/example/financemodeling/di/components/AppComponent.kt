package com.example.financemodeling.di.components

import com.example.financemodeling.di.modules.AppModules
import com.example.financemodeling.presenter.CompaniesPresenterImpl
import com.example.financemodeling.presenter.HistoriesPresenterImpl
import com.example.financemodeling.ui.MainActivity
import com.example.financemodeling.ui.fragments.CompaniesFragment
import com.example.financemodeling.ui.fragments.HistoriesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModules::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(companiesFragment: CompaniesFragment)
    fun inject(historiesFragment: HistoriesFragment)
    fun inject(historiesPresenter: HistoriesPresenterImpl)
    fun inject(companiesPresenter: CompaniesPresenterImpl)
}