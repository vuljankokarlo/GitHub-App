package com.assignment.githubapp.di

import com.assignment.githubapp.common.data.repositories.GitHubRepository
import com.assignment.githubapp.features.home.domain.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGitHubRepositoriesUseCases(
        gitHubRepository: GitHubRepository
    ): GitHubRepositoriesUseCases {
        return GitHubRepositoriesUseCases(
            getRepositoriesUseCase = GetRepositoriesUseCase(gitHubRepository),
            getRepositoryDetailsUseCase = GetRepositoryDetailsUseCase(gitHubRepository),
            getOwnerInfoUseCase = GetOwnerInfoUseCase(gitHubRepository),
            getAuthOwnerInfoUseCase = GetAuthOwnerInfoUseCase(gitHubRepository)
        )
    }
}