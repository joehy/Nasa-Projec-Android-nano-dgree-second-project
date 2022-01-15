package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding
import com.udacity.asteroidradar.model.Asteroid

class AsteroidsAdapter(val clickListener:AsteroidListener) : ListAdapter<Asteroid, AsteroidsAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(clickListener,item)
    }

    class ViewHolder private constructor(private val binding: AsteroidListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
            binding.clickIt=clickListener
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding : AsteroidListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
                    ,R.layout.asteroid_list_item,parent,false)
               return ViewHolder(binding)
           }
        }

    }
    class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem==newItem
        }
    }
    class AsteroidListener(val clickListener:(asteroid:Asteroid)->Unit){
        fun onClick (asteroid:Asteroid)=clickListener(asteroid)
    }

}