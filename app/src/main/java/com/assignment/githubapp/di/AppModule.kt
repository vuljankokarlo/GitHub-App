package com.assignment.githubapp.di

import com.assignment.githubapp.common.PreferencesManager
import com.assignment.githubapp.common.data.repositories.GitHubRepository
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.global.useCases.GlobalRepositoryUseCases
import com.assignment.githubapp.common.global.useCases.TempUseCase
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
        globalRepositoryUseCases: GlobalRepositoryUseCases
    ): GlobalRepository {
        return GlobalRepository(preferencesManager, globalRepositoryUseCases)
    }


    @Provides
    @Singleton
    fun provideGlobalRepositoryUseCases(
        githubRepository: GitHubRepository
    ): GlobalRepositoryUseCases {
        return GlobalRepositoryUseCases(
            tempUseCase = TempUseCase(githubRepository),
        )
    }
}