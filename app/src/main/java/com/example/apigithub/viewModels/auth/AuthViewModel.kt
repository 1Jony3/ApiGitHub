package com.example.apigithub.viewModels.auth

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.entities.UserInfo
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*

class AuthViewModel constructor(private val repository: Repository): ViewModel() {

    private val token = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null
    val message = MutableLiveData<String>()

    init {
        d("lol", "AuthViewModel")
        repository.keyValueStorage.user = null
        message.postValue("VTF")
    }

    /*sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }*/

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }

    fun onSignButtonPressed() {
        getUserLogin()
    }

    private fun getUserLogin(){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).async{
            val response = repository.signIn(token.value!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setKeyValue(response.body()!!, token.value!!)
                    _action.postValue(Action.RouteToMain)
                } else {
                    _action.postValue(Action.ShowError("Not search"))
                    errorMessage.postValue("Error ${response.message()}")
                }
            }
        }
    }


    private fun setKeyValue(userInfo: UserInfo, authToken: String) {
        repository.keyValueStorage.user = userInfo.login
        repository.keyValueStorage.token = authToken
    }

    fun getUserName(): String? = repository.keyValueStorage.user

    fun checkToken(authToken: String) {
        token.value = authToken
        d("lol", "check token - ${token.value}")
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}