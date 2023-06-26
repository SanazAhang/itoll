package com.example.itoll.presentation

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class Helper {
    companion object{
        fun glideCreator(imageView: ImageView,url:String, context:Context){
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        }
    }
}