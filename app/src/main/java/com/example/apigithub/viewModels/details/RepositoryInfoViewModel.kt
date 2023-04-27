package com.example.apigithub.viewModels.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.RepoDetails
import com.example.apigithub.model.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _readmeState = MutableLiveData<ReadMeState>()
    val readmeState: LiveData<ReadMeState> = _readmeState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private val errorMessage = MutableLiveData<String>()
    private var job: Job? = null

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Loaded(
            val githubRepo: RepoDetails,
            val readme: String
        ) : State
    }

    fun getRepo(nameRepository: String) {
        _state.postValue(State.Loading)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.getRepository(nameRepository)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    if (readmeState.value is ReadMeState.Loaded)
                        _state.postValue(State.Loaded(
                            response.body()!!,
                            (readmeState.value as ReadMeState.Loaded).markdown)
                        )
                    else {
                        _state.postValue(State.Loaded(
                                response.body()!!,
                                "ReadMe not create"
                            )
                        )
                    }

                } else {
                    _state.postValue(State.Error("Error ${response.message()}"))
                }
            }
        }
    }

    sealed interface ReadMeState {
        object Loading : ReadMeState
        object Empty : ReadMeState
        data class Error(val error: String) : ReadMeState
        data class Loaded(val markdown: String) : ReadMeState
    }

    fun getReadMe(nameRepository: String) {
        _readmeState.postValue(ReadMeState.Loading)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.getRepositoryReadme(nameRepository)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _readmeState.postValue(ReadMeState.Loaded(response.body()!!.content!!))

                } else {
                    if (response.body() == null) _readmeState.postValue(ReadMeState.Empty)
                    else _readmeState.postValue(ReadMeState.Error("Error ${response.message()}"))
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}