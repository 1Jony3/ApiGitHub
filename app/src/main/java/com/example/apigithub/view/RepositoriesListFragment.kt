package com.example.apigithub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apigithub.R
import com.example.apigithub.databinding.RepositoriesListFragmentBinding
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.RepositoryImp
import com.example.apigithub.presenter.adapter.GitHubAdapter
import com.example.apigithub.presenter.auth.viewModelFactory
import com.example.apigithub.presenter.clicker.OnClickListener
import com.example.apigithub.presenter.list.RepositoriesListViewModel

class RepositoriesListFragment : Fragment() {

    private lateinit var onClickListener: OnClickListener
    private lateinit var binding: RepositoriesListFragmentBinding
    private lateinit var  viewModel: RepositoriesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory(
                RepositoryImp(Common.retrofitService), KeyValueStorage(context!!.applicationContext)
            )
        ).get(RepositoriesListViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.repositories_list_fragment, container, false)
        binding = RepositoriesListFragmentBinding.bind(view)
        val layoutManager = LinearLayoutManager(view.context)
        binding.repositoriesList.layoutManager = layoutManager

        viewModel.repoList.observe(this, {

            binding.repositoriesList.adapter = setAdapter(it)
        })

        viewModel.getRepoList()

        return view
    }

    private fun setAdapter(repo: List<Repo>): GitHubAdapter{
        return GitHubAdapter(repo)
    }

}