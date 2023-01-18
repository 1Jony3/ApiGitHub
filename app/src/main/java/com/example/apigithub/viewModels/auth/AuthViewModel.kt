package com.example.apigithub.viewModels.auth

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.UserInfo
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class AuthViewModel constructor(private val repository: Repository): ViewModel() {

    private val token = MutableLiveData<String>()
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


    fun onSignButtonPressed() {
        getUserLogin()
    }
    private fun getUserLogin(){
        val hz = CoroutineScope(Dispatchers.IO + exceptionHandler).async{
            val response = repository.signIn(token.value!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setKeyValue(response.body()!!, token.value!!)
                    d("lol", "success and set key")
                    ///
                    d("lol", "State.Idle")
                    state.value = State.Idle
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

    fun checkToken(authToken: String) {
        d("lol", " check token - $authToken")
        //check token if ()
        token.value = authToken
        d("lol", " check token - ${token.value}")
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}