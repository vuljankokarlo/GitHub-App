package com.assignment.githubapp.di

import com.assignment.githubapp.BuildConfig.REMOTE_BASE_URL
import com.assignment.githubapp.common.data.dataSources.remote.GitHubAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BaseHttpClient")
    fun getBaseHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github+json")
                        .build()
                )
            }
            .build()
    }

    @Provides
    fun getBaseJsonConfig(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

    @BaseRetrofit
    @Provides
    fun getBaseRetrofit(@Named("BaseHttpClient") client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(REMOTE_BASE_URL)
            .build()
    }

    @Provides
    fun getGitHubApi(@BaseRetrofit retrofit: Retrofit): GitHubAPI =
        retrofit.create(GitHubAPI::class.java)
}