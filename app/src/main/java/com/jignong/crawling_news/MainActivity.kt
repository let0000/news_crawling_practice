package com.jignong.crawling_news

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jignong.crawling_news.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    companion object {
        private var TAG = "로그"
    }

    private val newsUrl = "https://news.naver.com/main/home.naver"
    var news : ArrayList<news> = arrayListOf()
    lateinit var news_recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        news_recyclerview = activityMainBinding.newsRecyclerview

        getNews(newsUrl)

        // 뉴스 카테고리 스피너 추가


    }

    private fun getNews(Url : String){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val headline = doc.select("#section_politics > div.com_list > div > ul")

            for (i in 0 until 5) {
                val title = headline.select("li a").get(i).text()
                val news_url = headline.select("li a").get(i).attr("href")
                val writing = headline.select("li span[class=writing]").get(i).text()
                val list = news(title, news_url, writing)
                news.add(list)
            }

            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "뉴스 : ${news[0].title}")
                Log.d(TAG, "링크 : ${news[0].news_url}")
                Log.d(TAG, "언론사 : ${news[0].writing} ")

                news_recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                news_recyclerview.adapter = MyAdapter(news)

            }
        }
    }
}

data class news (val title : String, val news_url : String, val writing : String)