package com.example.apigithub.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.repository.Repository
import com.example.apigithub.viewModels.list.RepositoriesListViewModel

class ViewModelFactory constructor(private val repository: Repository, private val keyValueStorage: KeyValueStorage):  ViewModelProvider.Factory{
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

