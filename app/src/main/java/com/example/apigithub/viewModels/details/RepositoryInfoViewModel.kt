package com.example.apigithub.viewModels.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.RepoDetails
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class RepositoryInfoViewModel constructor(private val repository: Repository): ViewModel() {

    val repo by lazy { MutableLiveData<RepoDetails>() }
    private val errorMessage = MutableLiveData<String>()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State

        data class Loaded(
            val githubRepo: RepoDetails,
            /*val readmeState: ReadmeState*/
        ) : State
    }
    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }

    fun getRepo(nameRepository: String){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.d("lol", "REPOSITORY ${repository.keyValueStorage.user!!}, ${repository.keyValueStorage.token!!}")

            val response = repository.getRepository(nameRepository)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("lol", "${response.body()}")

                    _state.postValue(State.Loaded(response.body()!!))

                    //repo.postValue(response.body())
                } else {
                    Log.d("lol", "ops")
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