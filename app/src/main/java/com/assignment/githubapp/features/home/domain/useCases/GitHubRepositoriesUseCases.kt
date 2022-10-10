package com.assignment.githubapp.features.home.domain.useCases

data class GitHubRepositoriesUseCases(
    val getRepositoriesUseCase: GetRepositoriesUseCase,
    val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    val getOwnerInfoUseCase: GetOwnerInfoUseCase
)