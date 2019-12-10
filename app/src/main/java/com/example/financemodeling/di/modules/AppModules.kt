package com.example.financemodeling.di.modules

import com.example.financemodeling.presenter.PresenterImpl
import com.example.financemodeling.interfaces.Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModules {

    @Provides
    @Singleton
    fun providePresenter(): Presenter {
        return PresenterImpl()
    }
}