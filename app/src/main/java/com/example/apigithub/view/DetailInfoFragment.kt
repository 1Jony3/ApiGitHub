package com.example.apigithub.view

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.apigithub.R
import com.example.apigithub.databinding.DetailInfoFragmentBinding
import com.example.apigithub.view.RepositoriesListFragment.Companion.ARG_REPO_NAME
import com.example.apigithub.viewModels.adapter.DetailsHolder
import com.example.apigithub.viewModels.auth.ViewModelFactory
import com.example.apigithub.viewModels.details.RepositoryInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailInfoFragment: Fragment(R.layout.detail_info_fragment) {

    private lateinit var binding: DetailInfoFragmentBinding

    @Inject lateinit var viewModelFactory: ViewModelFactory


    private  val viewModel: RepositoryInfoViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailInfoFragmentBinding.inflate(inflater, container, false)

        Log.d("lol", "$viewModelFactory")

        val nameRepository = requireArguments().getString(ARG_REPO_NAME)!!

        viewModel.getReadMe(nameRepository)

        val holder = DetailsHolder(binding.root)

        viewModel.readmeState.observe(viewLifecycleOwner) { readmeState ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (readmeState is RepositoryInfoViewModel.ReadMeState.Loaded ||
                    readmeState is RepositoryInfoViewModel.ReadMeState.Empty)
                    viewModel.getRepo(nameRepository)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.progressBar.visibility =
                    if (state == RepositoryInfoViewModel.State.Loading) View.VISIBLE else View.GONE

                binding.containerDetails.visibility =
                    if (state is RepositoryInfoViewModel.State.Loaded) View.VISIBLE else View.GONE

                binding.errorView.visibility =
                    if (state is RepositoryInfoViewModel.State.Error) View.VISIBLE else View.GONE

                binding.errorView.text = if (state is RepositoryInfoViewModel.State.Error) {
                    state.error
                } else {
                    null
                }

                if (state is RepositoryInfoViewModel.State.Loaded) {
                    holder.bind(state.githubRepo, decodeContext(state.readme))
                }
            }

        }
        return binding.root
    }

    private fun decodeContext(text: String) = String(Base64.decode(text, Base64.DEFAULT))
}