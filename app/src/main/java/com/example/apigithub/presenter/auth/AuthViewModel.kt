package com.example.apigithub.presenter.auth

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

    fun getUser(view: View, authToken: String) {
        keyValueStorage.token = authToken
        val response = repository.getUserInfo("Bearer $authToken")
        val thread = Thread {
            try {
                response.enqueue(object : Callback<UserInfo> {
                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>
                    ) {
                        response.body()?.let {
                            setData(it, view)
                            token.postValue(authToken)
                        }
                        token.postValue("not")
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

    fun setData(userInfo: UserInfo, view: View) {
        d("lol", "$userInfo")
        keyValueStorage.user = userInfo.login
    }
}