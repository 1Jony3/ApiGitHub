package com.example.apigithub.viewModels.adapter

import android.view.View
import android.widget.TextView
import com.example.apigithub.R
import com.example.apigithub.model.entities.RepoDetails

class DetailsHolder(itemView: View) {

    private val htmlURL: TextView = itemView.findViewById(R.id.link)
    private val forksCount: TextView = itemView.findViewById(R.id.yourFork)
    private val watchersCount: TextView = itemView.findViewById(R.id.yourWatchers)
    private val stargazersCount: TextView = itemView.findViewById(R.id.yourStar)
    private val license: TextView = itemView.findViewById(R.id.yourLicense)
    private val readMe: TextView = itemView.findViewById(R.id.readMeDescription)

    fun bind(repoDetails: RepoDetails, readMeContent: String?) {
        htmlURL.text = repoDetails.htmlURL
        forksCount.text = repoDetails.forksCount.toString()
        watchersCount.text = repoDetails.watchersCount.toString()
        stargazersCount.text = repoDetails.stargazersCount.toString()
        license.text = repoDetails.license
        readMe.text = readMeContent
    }
}