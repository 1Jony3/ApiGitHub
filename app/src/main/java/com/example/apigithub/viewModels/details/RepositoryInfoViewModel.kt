package com.example.apigithub.viewModels.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.RepoDetails
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class RepositoryInfoViewModel constructor(private val repository: Repository): ViewModel() {

    val repo by lazy { MutableLiveData<RepoDetails>() }
    private val errorMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null

    fun getRepo(nameRepository: String){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.d("lol", "REPOSITORY ${repository.keyValueStorage.user!!}, ${repository.keyValueStorage.token!!}")

            val response = repository.getRepository(nameRepository)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("lol", "${response.body()}")
                    repo.postValue(response.body())
                } else {
                    Log.d("lol", "ops")
                    errorMessage.postValue("Error ${response.message()}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}