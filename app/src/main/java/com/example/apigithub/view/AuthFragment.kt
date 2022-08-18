package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.FragmentAuthBinding
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.viewModels.auth.AuthViewModel
import com.example.apigithub.viewModels.auth.ViewModelFactory

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var  viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("lol", "auth")
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                AppRepository(Common.retrofitService), KeyValueStorage(context!!.applicationContext))
        ).get(AuthViewModel::class.java)

        d("lol", "create viewModel")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        binding = FragmentAuthBinding.bind(view)
        viewModel.token.observe(this, {
            viewModel.checkToken(it)
        })
        binding.singIn.setOnClickListener {
            viewModel.getUser(binding.textInputEditText.text.toString())
            if (viewModel.token.value != "not") openList(viewModel.getUserName()!!)
        }
        return view
    }

    private fun openList(userName: String){
        findNavController().navigate(
            R.id.action_authFragment_to_repositoriesListFragment2,
            bundleOf(RepositoriesListFragment.ARG_USER_NAME to userName)
        )
    }
}