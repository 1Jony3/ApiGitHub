package com.example.apigithub.model.api

import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IGitHubAPI {

    @GET("user")
    fun getUserInfo(@Header("Authorization") authHeader: String): Call<UserInfo>
    @GET("users/{userName}/repos")
    fun getRepositoriesList(
        @Path("userName") userName: String, @Header("Authorization") authHeader: String): Call<List<Repo>>

}