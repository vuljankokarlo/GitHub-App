package com.assignment.githubapp.di

import com.assignment.githubapp.common.data.dataSources.GitHubDataSource
import com.assignment.githubapp.common.data.repositories.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGitHubRepository(dataSource: GitHubDataSource): GitHubRepository {
        return GitHubRepository(dataSource)
    }
}