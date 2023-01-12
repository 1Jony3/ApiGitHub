package com.example.apigithub.viewModels.list

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class RepositoriesListViewModel constructor(private val repository: Repository): ViewModel() {

    val repoList by lazy { MutableLiveData<List<Repo>>() }
    private val errorMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null

    fun getRepo(){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            d("lol", "${repository.keyValueStorage.user!!}, ${repository.keyValueStorage.token!!}")
            val response = repository.getRepositories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    d("lol", "${response.body()}")
                    repoList.postValue(response.body())
                } else {
                    d("lol", "ooooops")
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

