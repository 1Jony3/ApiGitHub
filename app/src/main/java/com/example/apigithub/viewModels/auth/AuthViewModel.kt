package com.example.apigithub.viewModels.auth

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val token = MutableLiveData<String>()

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action> = _actions.receiveAsFlow()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }

    init {
        d("lol", "${repository.keyValueStorage.token}")
        if (repository.keyValueStorage.token != "") getUserLogin()
    }

    fun onSignButtonPressed() {
        setKeyValueToken(token.value!!)
        getUserLogin()
    }

    private fun getUserLogin(){
        _state.postValue(State.Loading)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).async{
            val response = repository.signIn(repository.keyValueStorage.token!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setKeyValueUser(response.body()!!.login)
                    _actions.send(Action.RouteToMain)
                    _state.postValue(State.Idle)
                } else {
                    _actions.send(Action.ShowError("response not success"))
                    _state.postValue(State.InvalidInput("${response.errorBody()}"))
                }
            }
        }
    }


    private fun setKeyValueUser(userLogin: String) {
        repository.keyValueStorage.user = userLogin
    }
    private fun setKeyValueToken(authToken: String) {
        repository.keyValueStorage.token = authToken
    }

    fun getUserName(): String? = repository.keyValueStorage.user

    fun checkToken(authToken: String): Boolean {
        return if (!authToken.contains(Regex("[^[a-zA-Z0-9_]*\$]"))) {
            token.value = authToken
            true
        } else false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}