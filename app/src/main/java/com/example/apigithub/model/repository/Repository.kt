package com.example.apigithub.model.repository

import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.entities.ReadMe
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.RepoDetails
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Response

interface Repository {
    var keyValueStorage: KeyValueStorage
    suspend fun signIn(authHeader: String): Response<UserInfo>
    suspend fun getRepositories(): Response<List<Repo>>
    suspend fun getRepository(nameRepository: String): Response<RepoDetails>
    suspend fun getRepositoryReadme(nameRepository: String): Response<ReadMe>
}

