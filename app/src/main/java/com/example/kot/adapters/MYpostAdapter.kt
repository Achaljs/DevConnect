package com.example.kot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot.R
import com.example.kot.databinding.MypostrvBinding
import com.example.kot.databinding.PostBinding
import com.example.kot.modles.posts
import com.example.kot.modles.users
import com.example.kot.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MYpostAdapter(var context:Context, var postList: ArrayList<posts>):RecyclerView.Adapter<MYpostAdapter.myHolder>(){


    inner class myHolder(var binding:MypostrvBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        var binding=MypostrvBinding.inflate(LayoutInflater.from(context),parent,false)
        return myHolder(binding)

    }

    override fun getItemCount(): Int {
        return postList.size

    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {




        Glide.with(context).load(postList.get(position).postImage).placeholder(R.drawable.loding).into(holder.binding.postImage)
holder.binding.likes.text=postList.get(position).Likedby.size.toString()

}



}