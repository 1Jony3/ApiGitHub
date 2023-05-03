package com.example.apigithub.model.repository

import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    override var keyValueStorage: KeyValueStorage)
    : Repository {

    private val gitHubApi = Common.retrofitService

    override suspend fun signIn(authHeader: String) = gitHubApi.signIn("Bearer $authHeader")

    override suspend fun getRepositories() = gitHubApi.getRepositories(
        userName = keyValueStorage.user!!,
        authHeader = "Bearer ${keyValueStorage.token}"
    )

    override suspend fun getRepository(nameRepository: String) = gitHubApi.getRepository(
        userName = keyValueStorage.user!!,
        nameRepository = nameRepository,
        authHeader = "Bearer ${keyValueStorage.token}"
    )

    override suspend fun getRepositoryReadme(nameRepository: String)= gitHubApi.getRepositoryReadme(
        userName = keyValueStorage.user!!,
        nameRepository = nameRepository,
        authHeader = "Bearer ${keyValueStorage.token}"
    )
}