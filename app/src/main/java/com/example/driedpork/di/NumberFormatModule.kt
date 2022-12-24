package com.example.driedpork.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.NumberFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NumberFormatModule {

    @Provides
    @Singleton
    @Named("currency")
    fun provideCurrencyNumberFormat(): NumberFormat {
        val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat
    }


    @Provides
    @Singleton
    @Named("basic")
    fun provideNumberFormat(): NumberFormat {
        return NumberFormat.getNumberInstance()
    }
}
