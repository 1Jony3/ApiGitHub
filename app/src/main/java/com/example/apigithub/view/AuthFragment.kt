package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.FragmentAuthBinding
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.viewModels.auth.AuthViewModel
import com.example.apigithub.viewModels.auth.ViewModelFactory

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    private  val viewModel: AuthViewModel by viewModels {
        ViewModelFactory(
            AppRepository(Common.retrofitService, KeyValueStorage(context!!.applicationContext))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("lol", "AuthFragment")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        viewModel.state.observe(this, {
            d("lol", "state observe $it")
            if (it == AuthViewModel.State.Idle) openList(viewModel.getUserName()!!)
        })
        binding.signIn.setOnClickListener {
            viewModel.checkToken(binding.textInputEditText.text.toString())
            viewModel.onSignButtonPressed()
        }
    }

    private fun openList(userName: String){
        findNavController().navigate(
            R.id.action_authFragment_to_repositoriesListFragment,
            bundleOf(RepositoriesListFragment.ARG_USER_NAME to userName)
        )
    }
}