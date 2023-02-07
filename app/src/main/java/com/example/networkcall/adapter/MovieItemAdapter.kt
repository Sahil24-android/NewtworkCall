package com.example.networkcall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkcall.R
import com.example.networkcall.data.DataItem
import org.w3c.dom.Text

class MovieItemAdapter: RecyclerView.Adapter<MovieItemAdapter.MovieViewHolder>() {
    private val list: ArrayList<DataItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       val mView = LayoutInflater.from(parent.context).inflate(R.layout.tv_item,parent,false)
        return MovieViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = list[position]

        Glide.with(holder.itemView.context).load(current.image).into(holder.image)
        holder.nameMovie.text = current.name
        holder.date.text = current.airdate
        holder.description.text = current.summary

    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun updateList(updateCourse: List<DataItem>){
        list.clear()
        list.addAll(updateCourse)
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.image)
        val nameMovie = itemView.findViewById<TextView>(R.id.name)
        val date = itemView.findViewById<TextView>(R.id.date)
        val description = itemView.findViewById<TextView>(R.id.description)
    }

}