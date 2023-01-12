package com.example.apigithub.model.repository

import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Response

interface Repository {
    var keyValueStorage: KeyValueStorage
    suspend fun signIn(authHeader: String): Response<UserInfo>
    suspend fun getRepositories(): Response<List<Repo>>
}

