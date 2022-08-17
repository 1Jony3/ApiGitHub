package com.example.apigithub.model.entities

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("html_url")
    val htmlURL: String,
    @SerializedName("name")
    val nameRepos: String,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val license: String?,
    val language: String,
    val description: String?
)

