package com.assignment.githubapp

import android.util.Log
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import io.realm.RealmSchema

private const val TAG = "DBMigrationHelper"

class DBMigrationHelper : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Log.i(TAG, "Old version: $oldVersion, new version: $newVersion")
        if (oldVersion < 1) {
            migration0to1(realm.schema)
        }
    }

    private fun migration0to1(schema: RealmSchema) {
        //TODO
    }
}