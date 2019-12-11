package com.example.financemodeling.di.modules

import com.example.financemodeling.api.RepoImpl
import com.example.financemodeling.api.interfaces.Repo
import com.example.financemodeling.presenter.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModules {

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter {
        return MainPresenterImpl()
    }

    @Provides
    @Singleton
    fun provideCompaniesPresenter(): CompaniesPresenter {
        return CompaniesPresenterImpl()
    }

    @Provides
    @Singleton
    fun provideHistoriesPresenter(): HistoriesPresenter {
        return HistoriesPresenterImpl()
    }

    @Provides
    @Singleton
    fun provideRepo(): Repo {
        return RepoImpl()
    }
}