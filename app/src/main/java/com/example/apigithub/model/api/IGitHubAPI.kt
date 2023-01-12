package com.example.apigithub.model.api

import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IGitHubAPI {

    @GET("user")
    suspend fun getUserInfo(@Header("Authorization") authHeader: String): Response<UserInfo>
    @GET("users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") userName: String, @Header("Authorization") authHeader: String): Response<List<Repo>>

}