package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class AuthFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        binding = FragmentAuthBinding.bind(view)
        viewModel.token.observe(this, {
            d("lol", "token observe $it")
            /*viewModel.checkToken()
            viewModel.getUserLogin(binding.textInputEditText.text.toString())*/

        })
        viewModel.state.observe(this, {
            d("lol", "state observe $it")
            if (it == AuthViewModel.State.Idle) openList(viewModel.getUserName()!!)
        })

        binding.singIn.setOnClickListener {
            viewModel.checkToken()
            viewModel.getUserLogin(binding.textInputEditText.text.toString())
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