package com.example.apigithub.viewModels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apigithub.databinding.ItemRepositoriesBinding
import com.example.apigithub.model.entities.Repo

interface OnClickRepositoryListener {
    fun onClick(github: String)
}

class RepoAdapter (
    private val onClickListener: OnClickRepositoryListener
) : RecyclerView.Adapter<RepoAdapter.GitHubHolder>() {

    var repos: List<Repo> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoriesBinding.inflate(inflater, parent, false)

        return GitHubHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubHolder, position: Int) {
        val repo = repos[position]
        holder.itemView.setOnClickListener {onClickListener.onClick(repo.nameRepos)}
        with(holder.binding){
            nameRepo.text = repo.nameRepos
            repo.description.let { description.text = it }
            language.text = repo.language
        }
    }

    override fun getItemCount() = repos.size

    class GitHubHolder(val binding: ItemRepositoriesBinding): RecyclerView.ViewHolder(binding.root)
}