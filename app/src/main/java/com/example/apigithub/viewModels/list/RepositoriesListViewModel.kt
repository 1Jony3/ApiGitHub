package com.example.apigithub.viewModels.list

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.Repository
import com.example.apigithub.viewModels.adapter.RepoAdapter
import com.example.apigithub.viewModels.clicker.OnClickRepositoryListener
import kotlinx.coroutines.*

class RepositoriesListViewModel constructor(private val repository: Repository): ViewModel() {

    private val _repoList = MutableLiveData<List<Repo>>()
    val repoList: LiveData<List<Repo>> = _repoList
    private val errorMessage = MutableLiveData<String>()

    var repoAdapter: RepoAdapter? = null

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null

    init {
        d("lol", "RepositoriesListViewModel")

    }

    fun getRepo() {
        d("lol", "getRepo RepositoriesListViewModel")
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            d("lol", "${repository.keyValueStorage.user!!}, ${repository.keyValueStorage.token!!}")
            val response = repository.getRepositories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    d("lol", "${response.body()}")
                    _repoList.postValue(response.body())
                } else {
                    d("lol", "ops")
                    errorMessage.postValue("Error ${response.message()}")
                }
            }
        }
    }

    fun setAdapter(onClickListener: OnClickRepositoryListener, repo: List<Repo>) {
        repoAdapter = RepoAdapter(onClickListener, repo)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

