package com.example.apigithub.viewModels.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesListViewModel constructor(private val repository: Repository, private val keyValueStorage: KeyValueStorage): ViewModel() {

    val repoList by lazy { MutableLiveData<List<Repo>>() }
    val errorMessage = MutableLiveData<String>()

    fun getRepoList() {
        val response = repository.getRepositories(keyValueStorage.user!!, keyValueStorage.token!!)
        val thread = Thread {
            try {
                response.enqueue(object : Callback<List<Repo>> {
                    override fun onResponse(
                        call: Call<List<Repo>>,
                        response: Response<List<Repo>>
                    ) {
                        repoList.postValue(response.body())
                    }

                    override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                        errorMessage.postValue(t.message)
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}

