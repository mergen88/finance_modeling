package com.example.financemodeling.di.components

import com.example.financemodeling.di.modules.AppModules
import com.example.financemodeling.ui.BaseActivity
import com.example.financemodeling.ui.fragments.CompaniesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModules::class))
interface AppComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(companiesFragment: CompaniesFragment)
}