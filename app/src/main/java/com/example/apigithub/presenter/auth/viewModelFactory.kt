package com.example.apigithub.presenter.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.repository.Repository
import com.example.apigithub.presenter.list.RepositoriesListViewModel

class viewModelFactory constructor(private val repository: Repository, private val keyValueStorage: KeyValueStorage):  ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            AuthViewModel::class.java -> {
                AuthViewModel(repository, keyValueStorage)
            }
            RepositoriesListViewModel::class.java -> {
                RepositoriesListViewModel(repository, keyValueStorage)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

