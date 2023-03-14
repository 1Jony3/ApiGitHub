package com.example.apigithub.viewModels.list

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.Repository
import com.example.apigithub.viewModels.adapter.RepoAdapter
import kotlinx.coroutines.*

class RepositoriesListViewModel constructor(private val repository: Repository): ViewModel() {

    private val _repoList = MutableLiveData<List<Repo>>()
    val repoList: LiveData<List<Repo>> = _repoList

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    var repoAdapter: RepoAdapter? = null

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        object Empty : State
    }

    init {
        _state.postValue(State.Loading)
    }

    fun getRepo() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            d("lol", "${repository.keyValueStorage.user!!}, ${repository.keyValueStorage.token!!}")
            val response = repository.getRepositories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    d("lol", "${response.body()}")
                    if (response.body() == null) _state.postValue(State.Empty)
                    else _state.postValue(State.Loaded(response.body()!!))

                    //_repoList.postValue(response.body())
                } else {
                    _state.postValue(State.Error("Error ${response.message()}"))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

