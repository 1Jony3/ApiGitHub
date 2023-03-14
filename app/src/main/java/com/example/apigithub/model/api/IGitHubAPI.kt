package com.example.apigithub.model.api

import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.RepoDetails
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IGitHubAPI {
    @GET("user")
    suspend fun signIn(
        @Header("Authorization") authHeader: String
    ): Response<UserInfo>

    @GET("users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") userName: String,
        @Header("Authorization") authHeader: String
    ): Response<List<Repo>>

    @GET("repos/{userName}/{nameRepository}")
    suspend fun getRepository(
        @Path("userName") userName: String,
        @Path("nameRepository") nameRepository: String,
        @Header("Authorization") authHeader: String
    ): Response<RepoDetails>
//@Query("ref") branchName: String
    @GET("repos/{userName}/{nameRepository}/contents/README.md?ref={branchName}")
    suspend fun getRepositoryReadme(
        @Path("userName") userName: String,
        @Path("nameRepository") nameRepository: String,
        @Path("branchName") branchName: String,
        @Header("Authorization") authHeader: String
    ): Response<String>
}