package com.example.apigithub.viewModels.auth

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apigithub.model.entities.UserInfo
import com.example.apigithub.model.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class AuthViewModel constructor(private val repository: Repository): ViewModel() {

    private val token = MutableLiveData<String>()

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action> = _actions.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()


    init {
        repository.keyValueStorage.user = null
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

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
                    _actions.send(Action.RouteToMain)
                } else {
                    _actions.send(Action.ShowError("response not success"))
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
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}