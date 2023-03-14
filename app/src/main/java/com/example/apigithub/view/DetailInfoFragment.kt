package com.example.apigithub.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apigithub.R
import com.example.apigithub.databinding.DetailInfoFragmentBinding
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.view.RepositoriesListFragment.Companion.ARG_REPO_NAME
import com.example.apigithub.viewModels.adapter.DetailsHolder
import com.example.apigithub.viewModels.auth.ViewModelFactory
import com.example.apigithub.viewModels.details.RepositoryInfoViewModel

class DetailInfoFragment: Fragment(R.layout.detail_info_fragment) {

    private lateinit var binding: DetailInfoFragmentBinding

    private  val viewModel: RepositoryInfoViewModel by viewModels {
        ViewModelFactory(
            AppRepository(Common.retrofitService, KeyValueStorage(context!!.applicationContext))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailInfoFragmentBinding.bind(view)
        Log.d("lol", "nn DETAILS - ${findNavController().currentDestination}")
        viewModel.getRepo(requireArguments().getString(ARG_REPO_NAME)!!)

        val holder = DetailsHolder(view)

        viewModel.state.observe(viewLifecycleOwner) { state ->
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

            if (state is RepositoryInfoViewModel.State.Loaded){
                holder.bind(state.githubRepo)
            }

        }

        /*viewModel.repo.observe(this, {
            holder.bind(it)
            Log.d("lol", "holder")
        })*/

    }
}