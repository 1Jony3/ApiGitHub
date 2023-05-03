package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.FragmentAuthBinding
import com.example.apigithub.viewModels.auth.AuthViewModel
import com.example.apigithub.viewModels.auth.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    companion object{
        const val ARG_USER_NAME = "userName"
    }

    private lateinit var binding: FragmentAuthBinding

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private  val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        d("lol", "$viewModelFactory")

        binding = FragmentAuthBinding.bind(view)

        //binding.webBrowser.loadUrl("https://raw.githubusercontent.com/1Jony3/ApiGitHub/master/README.md")

        binding.signIn.setOnClickListener {
            val token = binding.textInputEditText.text.toString()
            if (viewModel.checkToken(token)) viewModel.onSignButtonPressed()
            else invalidToken()
        }

        lifecycleScope.launch {
            viewModel.actions.collect { handleAction(it) }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.visibility =
                if (state == AuthViewModel.State.Loading) View.VISIBLE else View.GONE

            if (state is AuthViewModel.State.InvalidInput)
                showError(state.reason)
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

    private fun invalidToken(){
        binding.textInputLayout.error = "Invalid token"
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}