package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.FragmentAuthBinding
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.viewModels.auth.AuthViewModel
import com.example.apigithub.viewModels.auth.ViewModelFactory
import kotlinx.coroutines.launch

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    private  val viewModel: AuthViewModel by viewModels {
        ViewModelFactory(
            AppRepository(Common.retrofitService, KeyValueStorage(context!!.applicationContext))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAuthBinding.bind(view)

        binding.signIn.setOnClickListener {
            viewModel.checkToken(binding.textInputEditText.text.toString())
            viewModel.onSignButtonPressed()
        }

        lifecycleScope.launch {
            viewModel.actions.collect { handleAction(it) }
        }
    }

    private fun handleAction(action: AuthViewModel.Action) {
        when (action) {
            AuthViewModel.Action.RouteToMain -> openList(viewModel.getUserName()!!)
            is AuthViewModel.Action.ShowError -> showError(action.message)
        }
    }

    private fun openList(userName: String){
        findNavController().navigate(
            R.id.action_authFragment_to_repositoriesListFragment,
            bundleOf(ARG_USER_NAME to userName)
        )
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val ARG_USER_NAME = "userName"
    }
}