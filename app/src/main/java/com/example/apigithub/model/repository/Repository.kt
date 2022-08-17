package com.example.apigithub.model.repository

import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.entities.UserInfo
import retrofit2.Call

interface Repository {
    fun getUserInfo(authHeader: String): Call<UserInfo>
    fun getRepositories(login: String, token: String): Call<List<Repo>>
}

