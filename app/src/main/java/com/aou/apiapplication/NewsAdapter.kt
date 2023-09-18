package com.aou.apiapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aou.apiapplication.model.ArticlesItem
import com.bumptech.glide.Glide

class NewsAdapter (var items : List<ArticlesItem>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val title: TextView = itemview.findViewById(R.id.title)
        val time: TextView = itemview.findViewById(R.id.time)
        val Author: TextView = itemview.findViewById(R.id.author)
        val Image: ImageView = itemview.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val View = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)

        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)

        holder.title.setText(item?.title)
        holder.Author.setText(item?.author)
        holder.time.setText(item?.publishedAt)

        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.Image)


    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun changedata (data : List<ArticlesItem>?){

        items = data
        notifyDataSetChanged()

    }

}