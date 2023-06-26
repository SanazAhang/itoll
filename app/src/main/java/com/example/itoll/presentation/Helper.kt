package com.example.itoll.presentation

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.HttpException
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.IOException

class Helper {
    companion object{
        fun glideCreator(imageView: ImageView,url:String, context:Context){
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        }

        fun asNetworkException(ex: Throwable): String {
            return when (ex) {
                is IOException -> {
                        "No Internet Connection"
                }
                is HttpException -> extractHttpExceptions(ex)
                else ->"Something went wrong..."
            }
        }

        private fun extractHttpExceptions(ex: HttpException): String {
            val body = ex.message
            val gson = GsonBuilder().create()
            val responseBody= gson.fromJson(body.toString(), JsonObject::class.java)
            val statusCode = ex.statusCode
            return when (statusCode) {
                BAD_REQUEST ->
                    "BadRequest Error"

                UNAUTHORIZED ->
                    "UnAuthorized Error"

                NOT_FOUND ->
                   "NotFound Error"

                else -> {
                    "unknown Error"
                }
            }
        }


    }


}
const val BAD_REQUEST = 400
const val UNAUTHORIZED = 401
const val NOT_FOUND = 404