package com.assignment.githubapp.features.home.domain.useCases

import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.data.repositories.GitHubRepository

class GetRepositoriesUseCase(private val gitHubRepository: GitHubRepository) {

    suspend operator fun invoke(gitHubRepositoriesRequest: GitHubRepositoriesRequest) =
        gitHubRepository.repositories(gitHubRepositoriesRequest)
}