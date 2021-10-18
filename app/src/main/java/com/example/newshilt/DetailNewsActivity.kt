package com.example.newshilt

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newshilt.ui.theme.NewsHiltTheme

class DetailNewsActivity : ComponentActivity() {
    lateinit var newsUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsHiltTheme {
                newsUrl = intent?.getStringExtra("news_url").toString()
                ShowNews(url = newsUrl)
            }
        }
    }
}

@Composable
fun ShowNews(url: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "News Detail Activity") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AndroidView(factory = {
                WebView(it).apply {
                    webViewClient = WebViewClient()
                    loadUrl(url)
                    settings.javaScriptEnabled = true
                }
            })
        }
    }

}
