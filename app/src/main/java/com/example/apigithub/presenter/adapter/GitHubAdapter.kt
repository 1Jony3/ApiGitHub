package com.example.apigithub.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apigithub.R
import com.example.apigithub.model.entities.Repo
import com.example.apigithub.presenter.clicker.OnClickListener

class GitHubAdapter(/*private val onClickListener: OnClickListener, */private val repoList: List<Repo>): RecyclerView.Adapter<GitHubAdapter.GitHubHolder>() {

    private var githubs: List<Repo> = repoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubHolder {
        return GitHubHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repositories, parent, false))
    }

    override fun onBindViewHolder(holder: GitHubHolder, position: Int) {
        val github = githubs[position]
        //holder.itemView.setOnClickListener { onClickListener.onClick(github) }
        holder.bind(github)
    }

    override fun getItemCount() = githubs.size

    class GitHubHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameRepos: TextView = itemView.findViewById(R.id.nameRep)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val language: TextView = itemView.findViewById(R.id.language)

        fun bind(repo: Repo) {

            nameRepos.text = repo.nameRepos
            description.text = repo.description
            language.text = repo.language

        }
    }
}