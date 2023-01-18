package com.example.apigithub.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apigithub.R
import com.example.apigithub.databinding.RepositoriesListFragmentBinding
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.viewModels.adapter.RepoAdapter
import com.example.apigithub.viewModels.auth.ViewModelFactory
import com.example.apigithub.viewModels.clicker.OnClickRepositoryListener
import com.example.apigithub.viewModels.list.RepositoriesListViewModel

class RepositoriesListFragment : Fragment(R.layout.repositories_list_fragment) {

    private lateinit var binding: RepositoriesListFragmentBinding

    private val onClickListener = object : OnClickRepositoryListener {
        override fun onClick(github: String) {
            openDetails(github)
        }
    }

    private  val viewModel: RepositoriesListViewModel by viewModels {
        ViewModelFactory(
            AppRepository(Common.retrofitService, KeyValueStorage(context!!.applicationContext))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lol", "onCreate RepositoriesListFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RepositoriesListFragmentBinding.bind(view)
        val layoutManager = LinearLayoutManager(view.context)
        binding.repositoriesList.layoutManager = layoutManager

        viewModel.getRepo()

        viewModel.repoList.observe(this, {
            binding.repositoriesList.adapter = setAdapter(it)
            Log.d("lol", "adapter")
        })

        Log.d("lol", "getRepo")
    }

    private fun setAdapter(repo: List<Repo>) = RepoAdapter(onClickListener, repo)

    private fun openDetails(repoName: String){
        findNavController().navigate(
            R.id.action_repositoriesListFragment_to_detailsFragment,
            bundleOf(DetailInfoFragment.ARG_REPO_NAME to repoName)
        )
    }

    companion object{
        const val ARG_USER_NAME = "userName"
    }

}