package com.jignong.crawling_news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jignong.crawling_news.databinding.ItemRecyclerviewBinding


class MyAdapter(val items: ArrayList<news>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val TAG = "Adapter"

    class ViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(ItemRecyclerviewBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.binding.newsTextview.text = items[position].title
        holder.binding.writingTextview.text = items[position].writing

        // 뉴스 클릭 시 링크 이동 추가
    }

    override fun getItemCount(): Int {
        return items.size
    }
}