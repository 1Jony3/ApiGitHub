package com.example.apigithub.viewModels.auth

import android.util.Log.d
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.entities.UserInfo
import com.example.apigithub.model.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel constructor(private val repository: Repository, private val keyValueStorage: KeyValueStorage): ViewModel() {

    val token = MutableLiveData<String>()

    fun getUser(authToken: String) {
        keyValueStorage.token = authToken
        val response = repository.getUserInfo("Bearer $authToken")
        val thread = Thread {
            try {
                response.enqueue(object : Callback<UserInfo> {
                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>
                    ) {
                        token.postValue("not")
                        response.body()?.let {
                            setData(it)
                            token.postValue(authToken)
                        }
                    }

                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    fun setData(userInfo: UserInfo) {
        d("lol", "$userInfo")
        keyValueStorage.user = userInfo.login
    }

    fun getUserName(): String? = keyValueStorage.user

    fun checkToken(token: String?){

    }
}