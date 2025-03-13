package com.example.restoapp.di

import com.example.core.domain.usecase.RestaurantInteractor
import com.example.core.domain.usecase.RestaurantUseCase
import com.example.restoapp.presentation.detail.DetailViewModel
import com.example.restoapp.presentation.home.HomeViewModel
import com.example.restoapp.presentation.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RestaurantUseCase> { RestaurantInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}