package com.example.apigithub.viewModels.clicker

import com.example.apigithub.model.entities.Repo

interface OnClickListener {
    fun onClick(github: Repo)
}

