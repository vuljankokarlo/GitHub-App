package com.assignment.githubapp.features.home.domain.useCases

import com.assignment.githubapp.common.data.repositories.GitHubRepository

class GetAuthOwnerInfoUseCase(private val gitHubRepository: GitHubRepository) {

    suspend operator fun invoke() =
        gitHubRepository.authOwnerInfo()
}