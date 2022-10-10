package com.assignment.githubapp.features.home.domain.useCases

import com.assignment.githubapp.common.data.repositories.GitHubRepository

class GetOwnerInfoUseCase(private val gitHubRepository: GitHubRepository) {

    suspend operator fun invoke(username: String) =
        gitHubRepository.ownerInfo(username)
}