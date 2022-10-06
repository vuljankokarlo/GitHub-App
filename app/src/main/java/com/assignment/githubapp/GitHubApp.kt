package com.assignment.githubapp

import android.app.Application
import com.assignment.githubapp.common.domain.dbmigration.DBMigrationHelper
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration


@HiltAndroidApp
class GitHubApp : Application() {

    companion object {
        const val TAG = "GitHubApp"
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration
                .Builder()
                .schemaVersion(BuildConfig.REALM_DB_VERSION)
                .migration(DBMigrationHelper())
                .build()
        )
    }
}