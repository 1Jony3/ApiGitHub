package com.example.apigithub.model.entities

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name")
    val nameRepos: String,
    val language: String,
    val description: String?
)

