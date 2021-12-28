package com.jignong.crawling_news

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

    private val newsUrl = "https://news.naver.com/main/tv/index.naver?mid=tvh"
    var news : ArrayList<news> = arrayListOf()
    lateinit var news_recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        news_recyclerview = activityMainBinding.newsRecyclerview

        //뉴스 카테고리 스피너 추가
        var category = resources.getStringArray(R.array.category)
        var news_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category)
        activityMainBinding.newsSpinner.adapter = news_adapter
        activityMainBinding.newsSpinner.setSelection(0)
        activityMainBinding.newsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        news.clear()
                        getNews(newsUrl, "2")
                    }
                    1 -> {
                        news.clear()
                        getNews(newsUrl, "3")
                    }
                    2 -> {
                        news.clear()
                        getNews(newsUrl, "4")
                    }
                    3 -> {
                        news.clear()
                        getNews(newsUrl, "5")
                    }
                    4 -> {
                        news.clear()
                        getNews(newsUrl, "6")
                    }
                    5 -> {
                        news.clear()
                        getNews(newsUrl, "7")
                    }
                    6 -> {
                        news.clear()
                        getNews(newsUrl, "8")
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    private fun getNews(Url : String , category : String){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val headline = doc.select("#wrap > table > tbody > tr > td.content > div > div > div:nth-child($category) > ul")


            for (i in 0 until 4) {
                val title = headline.select("li p").get(i).text()
                val news_url = headline.select("li a").get(i).attr("href")
                val list = news(title, news_url)
                news.add(list)
            }

            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "뉴스 : ${news[0].title}, ${news[1].title}")
                Log.d(TAG, "링크 : ${news[0].news_url} , ${news[1].news_url}")

                news_recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                news_recyclerview.adapter = MyAdapter(news)

            }
        }
    }
}

data class news (val title : String, val news_url : String)