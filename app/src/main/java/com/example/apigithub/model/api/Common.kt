package com.example.apigithub.model.api

object Common {
    private const val baseUrl = "https://api.github.com/"
    val retrofitService: IGitHubAPI
        get() = RetrofitClient.getClient(baseUrl).create(IGitHubAPI::class.java)
}