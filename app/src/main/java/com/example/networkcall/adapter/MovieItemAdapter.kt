package com.example.networkcall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkcall.R
import com.example.networkcall.data.DataItem

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

        holder.description.setOnClickListener {
            if (holder.description.maxLines == Integer.MAX_VALUE){
                holder.description.maxLines =2
            }else{
                holder.description.maxLines = Integer.MAX_VALUE
            }
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun updateList(updateCourse: List<DataItem>){
        list.clear()
        list.addAll(updateCourse)
        notifyDataSetChanged()
    }
    fun deleteTVShow(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class MovieViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.image)
        val nameMovie = itemView.findViewById<TextView>(R.id.name)
        val date = itemView.findViewById<TextView>(R.id.date)
        val description = itemView.findViewById<TextView>(R.id.description)
    }

     class SwipeToDeleteCallback(private val adapter: MovieItemAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            adapter.deleteTVShow(position)

        }
    }

}