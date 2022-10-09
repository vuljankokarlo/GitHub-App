package com.assignment.githubapp.features.home.domain.useCases

import com.assignment.githubapp.common.data.repositories.GitHubRepository

class GetRepositoryDetailsUseCase(private val gitHubRepository: GitHubRepository) {

    suspend operator fun invoke(repositoryId: Int) =
        gitHubRepository.repositoryDetails(repositoryId)
}