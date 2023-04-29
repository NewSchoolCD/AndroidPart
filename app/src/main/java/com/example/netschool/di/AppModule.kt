package com.example.netschool.di

import android.content.Context
import com.example.netschool.adapters.FBTools
import com.example.netschool.repositories.FBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context{
        return context
    }

    @Provides
    fun provideFBRepository() = FBRepository(FBTools())
}