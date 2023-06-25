package com.example.itoll.presentation.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.itoll.databinding.ItemUserBinding
import com.example.itoll.domain.model.UserModel

class UserAdapter(val users: List<UserModel>, val onClick: (UserModel) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    lateinit var contex :Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        contex = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userName.text = users[position].login
        val radius = 30
        val margin = 10
        users[position].avatar_url?.let{imageUrl ->
            Glide.with(contex)
                .load(imageUrl)
                .centerCrop()
                .into(holder.binding.avatarImageView)
        }

        holder.itemView.setOnClickListener {
            onClick.invoke(users[position])
        }
    }


    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}