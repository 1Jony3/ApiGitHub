package com.example.apigithub.view

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.RepositoriesListFragmentBinding
import com.example.apigithub.viewModels.adapter.OnClickRepositoryListener
import com.example.apigithub.viewModels.adapter.RepoAdapter
import com.example.apigithub.viewModels.auth.ViewModelFactory
import com.example.apigithub.viewModels.list.RepositoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(R.layout.repositories_list_fragment) {

    companion object{
        const val ARG_REPO_NAME = "repoName"
    }

    private lateinit var binding: RepositoriesListFragmentBinding
    private lateinit var adapter: RepoAdapter

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RepositoriesListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepositoriesListFragmentBinding.inflate(inflater, container, false)
        adapter = RepoAdapter(object : OnClickRepositoryListener{
            override fun onClick(github: String) {
                openDetails(github)
            }

        })

        viewModel.getRepo()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {

                binding.progressBar.visibility =
                    if (state == RepositoriesListViewModel.State.Loading) View.VISIBLE else View.GONE
                binding.list.visibility =
                    if (state is RepositoriesListViewModel.State.Loaded) View.VISIBLE else View.GONE
                binding.errorView.visibility =
                    if (state is RepositoriesListViewModel.State.Error) View.VISIBLE else View.GONE
                binding.errorView.text = if (state is RepositoriesListViewModel.State.Error) {
                    state.error
                } else {
                    null
                }
                d("lol", "state ${viewModel.state} and ${viewModel.repoAdapter}")

                viewModel.repoAdapter = setAdapter(state)
                binding.list.adapter = adapter

            }
        }

        return binding.root
    }

    private fun setAdapter(state: RepositoriesListViewModel.State): RepoAdapter {
        if (state is RepositoriesListViewModel.State.Loaded)
            adapter.repos = state.repos
        return adapter
    }

    private fun openDetails(repoName: String){
        findNavController().navigate(
            R.id.action_repositoriesListFragment_to_detailsFragment,
            bundleOf(ARG_REPO_NAME to repoName)
        )
    }
}