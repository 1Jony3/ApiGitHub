package com.example.apigithub.presenter.clicker

import com.example.apigithub.model.entities.Repo

interface OnClickListener {
    fun onClick(github: Repo)
}

