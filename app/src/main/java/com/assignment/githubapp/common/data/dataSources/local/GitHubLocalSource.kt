package com.assignment.githubapp.common.data.dataSources.local

import com.assignment.githubapp.common.data.models.db.GitHubUser
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class GitHubLocalSource @Inject constructor() {
    suspend fun getUser(): RealmResults<GitHubUser> {
        val realm = Realm.getDefaultInstance()
        return realm.where(GitHubUser::class.java).findAll()
    }

    suspend fun saveUser(gitHubUser: GitHubUser) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.insertOrUpdate(gitHubUser)
        realm.commitTransaction()
        realm.close()
    }
}