package com.example.apigithub.viewModels.auth

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.UserInfo
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class AuthViewModel constructor(private val repository: Repository): ViewModel() {

    val token = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null
    val state :MutableLiveData<State> = MutableLiveData(State.Loading)

    init {
        d("lol", "AuthViewModel")
        repository.keyValueStorage.user = null

    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }


    /*fun onSignButtonPressed(authToken: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.signIn(authToken)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setKeyValue(response.body()!!, authToken)
                    d("lol", "success and set key")
                    token.postValue(authToken)
                } else {
                    errorMessage.postValue("Error ${response.message()}")
                }
        }
        }
    }*/
    fun getUserLogin(authToken: String){
        val hz = CoroutineScope(Dispatchers.IO + exceptionHandler).async{
            val response = repository.signIn(authToken)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setKeyValue(response.body()!!, authToken)
                    d("lol", "success and set key")
                    token.postValue(authToken)
                    ///
                    d("lol", "State.Idle")
                    state.postValue(State.Idle)
                } else {
                    errorMessage.postValue("Error ${response.message()}")
                }
            }
        }
        d("lol", "getUserLogin - $hz")
    }


    private fun setKeyValue(userInfo: UserInfo, authToken: String) {
        d("lol", " set $userInfo")
        repository.keyValueStorage.user = userInfo.login
        repository.keyValueStorage.token = authToken
    }

    fun getUserName(): String? = repository.keyValueStorage.user

    fun checkToken() {
        d("lol", " check token - ${token.value}")
        //check token
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}