package com.example.apigithub.viewModels.adapter

import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.example.apigithub.R
import com.example.apigithub.model.entities.RepoDetails
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class DetailsHolder(itemView: View) {

    private val htmlURL: TextView = itemView.findViewById(R.id.link)
    private val forksCount: TextView = itemView.findViewById(R.id.yourFork)
    private val watchersCount: TextView = itemView.findViewById(R.id.yourWatchers)
    private val stargazersCount: TextView = itemView.findViewById(R.id.yourStar)
    private val license: TextView = itemView.findViewById(R.id.yourLicense)
    private val readMe: WebView = itemView.findViewById(R.id.readMeDescription)

    fun bind(repoDetails: RepoDetails, readMeContent: String?) {
        htmlURL.text = repoDetails.htmlURL
        forksCount.text = repoDetails.forksCount.toString()
        watchersCount.text = repoDetails.watchersCount.toString()
        stargazersCount.text = repoDetails.stargazersCount.toString()
        license.text = repoDetails.license

        if (readMeContent != null) {
            readMe.setBackgroundColor(0x00000000)

            val parser: Parser = Parser.builder().build()
            val document: Node = parser.parse(readMeContent)
            val renderer = HtmlRenderer.builder().build()
            val html: String = renderer.render(document)

            readMe.loadDataWithBaseURL(null, "<font color='white'>$html</font>", "text/html", "en_US", null)
        }
    }
}