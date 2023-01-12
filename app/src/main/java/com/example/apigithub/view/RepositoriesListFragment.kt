package com.example.apigithub.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apigithub.R
import com.example.apigithub.databinding.RepositoriesListFragmentBinding
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.viewModels.adapter.RepoAdapter
import com.example.apigithub.viewModels.auth.ViewModelFactory
import com.example.apigithub.viewModels.list.RepositoriesListViewModel

class RepositoriesListFragment : Fragment() {

    //private lateinit var onClickListener: OnClickListener
    private lateinit var binding: RepositoriesListFragmentBinding

    private  val viewModel: RepositoriesListViewModel by viewModels {
        ViewModelFactory(
            AppRepository(Common.retrofitService, KeyValueStorage(context!!.applicationContext))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lol", "onCreate RepositoriesListFragment")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("lol", "onCreateView")
        val view =  inflater.inflate(R.layout.repositories_list_fragment, container, false)
        binding = RepositoriesListFragmentBinding.bind(view)
        val layoutManager = LinearLayoutManager(view.context)
        binding.repositoriesList.layoutManager = layoutManager

        viewModel.getRepo()

        viewModel.repoList.observe(this, {
            binding.repositoriesList.adapter = setAdapter(it)
            Log.d("lol", "adapter")
        })

        Log.d("lol", "getRepo")

        return view
    }

    private fun setAdapter(repo: List<Repo>) = RepoAdapter(repo)

    companion object{
        const val ARG_USER_NAME = "userName"
    }

}