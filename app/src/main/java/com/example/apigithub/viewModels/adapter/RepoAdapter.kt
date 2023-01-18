package com.example.apigithub.viewModels.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apigithub.R
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.viewModels.clicker.OnClickRepositoryListener

class RepoAdapter(private val onClickListener: OnClickRepositoryListener, repoList: List<Repo>): RecyclerView.Adapter<RepoAdapter.GitHubHolder>() {

    private var repositoriesGitHub: List<Repo> = repoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubHolder {
        return GitHubHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repositories, parent, false))
    }

    override fun onBindViewHolder(holder: GitHubHolder, position: Int) {
        val github = repositoriesGitHub[position]
        holder.itemView.setOnClickListener { onClickListener.onClick(github.nameRepos) }
        holder.bind(github)
    }

    override fun getItemCount() = repositoriesGitHub.size

    class GitHubHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameRepos: TextView = itemView.findViewById(R.id.nameRep)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val language: TextView = itemView.findViewById(R.id.language)

        fun bind(repo: Repo) {
            nameRepos.text = repo.nameRepos
            repo.description.let { description.text = it }
            language.text = repo.language
        }
    }
}