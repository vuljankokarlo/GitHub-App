package com.assignment.githubapp.di

import com.assignment.githubapp.common.PreferencesManager
import com.assignment.githubapp.common.global.GlobalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        preferencesManager: PreferencesManager,
    ): GlobalRepository {
        return GlobalRepository(preferencesManager)
    }
}