package com.example.apigithub.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apigithub.R
import com.example.apigithub.databinding.DetailInfoFragmentBinding
import com.example.apigithub.model.KeyValueStorage
import com.example.apigithub.model.api.Common
import com.example.apigithub.model.repository.AppRepository
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

        viewModel.getRepo(requireArguments().getString(ARG_REPO_NAME)!!)

        val holder = DetailsHolder(view)
        viewModel.repo.observe(this, {
            holder.bind(it)
            Log.d("lol", "holder")
        })

    }

    companion object{
        const val ARG_REPO_NAME = "repoName"
    }
}