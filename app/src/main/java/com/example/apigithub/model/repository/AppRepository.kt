package com.example.apigithub.model.repository

import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.IGitHubAPI
import com.example.apigithub.model.entities.RepoDetails

class AppRepository constructor(private val gitHubApi: IGitHubAPI, override var keyValueStorage: KeyValueStorage): Repository {

    override suspend fun signIn(authHeader: String) = gitHubApi.getUserInfo("Bearer $authHeader")

    override suspend fun getRepositories() = gitHubApi.getRepositories(
        userName = keyValueStorage.user!!,
        authHeader = "Bearer ${keyValueStorage.token}"
    )

    override suspend fun getRepository(nameRepository: String) = gitHubApi.getRepository(
        userName = keyValueStorage.user!!,
        nameRepository = nameRepository,
        authHeader = "Bearer ${keyValueStorage.token}"
    )
}