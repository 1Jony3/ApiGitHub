package com.example.apigithub.model.repository

import com.example.apigithub.model.api.IGitHubAPI
import com.example.apigithub.model.entities.Repo
import retrofit2.Call

class AppRepository constructor(private val gitHubApi: IGitHubAPI): Repository {//getRepositoriesList(key.user!!,"Bearer ${key.token}")
    override fun getUserInfo(authHeader: String) = gitHubApi.getUserInfo(authHeader)
    override fun getRepositories(login: String, token: String): Call<List<Repo>> = gitHubApi.getRepositoriesList(login,"Bearer $token")
}