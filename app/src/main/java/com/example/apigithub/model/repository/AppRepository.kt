package com.example.apigithub.model.repository

import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.IGitHubAPI

class AppRepository constructor(private val gitHubApi: IGitHubAPI, override var keyValueStorage: KeyValueStorage): Repository {
    override suspend fun getRepositories() = gitHubApi.getRepositories(
        userName = keyValueStorage.user!!,
        authHeader = "Bearer ${keyValueStorage.token}"
    )

    override suspend fun signIn(authHeader: String) = gitHubApi.getUserInfo("Bearer $authHeader")
}