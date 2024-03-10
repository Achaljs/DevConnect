package com.example.kot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot.R
import com.example.kot.databinding.StoryrvlayoutBinding
import com.example.kot.modles.storys
import com.example.kot.modles.users
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.detail
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory


class StoryAdapter(var context: Context,var storyList: ArrayList<storys>):RecyclerView.Adapter<StoryAdapter.viewHolder>(){



    inner class viewHolder(var binding:StoryrvlayoutBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding=StoryrvlayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
       return storyList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {


var name=""
        var profileimg=""

        try {

            Firebase.firestore.collection(USER_NODE).document(storyList.get(position).storyBy).collection(
                detail
            ).document("MY").get()
                .addOnSuccessListener {
                    var user = it.toObject<users>()

                    Glide.with(context).load(user!!.image).placeholder(R.drawable.loding)
                        .into(holder.binding.profile)
name=user.names.toString()
                    profileimg=user.image.toString()





                }.addOnFailureListener {
                    Toast.makeText(context,it.localizedMessage, Toast.LENGTH_SHORT).show()
                }

        }
        catch (e:Exception){
            Toast.makeText(context,e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        holder.binding.circularStatusView.setPortionsCount(storyList.get(position).stories.size)

        holder.binding.profile.setOnClickListener {


            val myStories = ArrayList<MyStory>()

            for (story in storyList.get(position).stories) {
                myStories.add(
                    MyStory(
                        story.imgUrl,

                    )
                )
            }

            StoryView.Builder((context as AppCompatActivity).supportFragmentManager)
                .setStoriesList(myStories) // Required
                .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                .setTitleText(name) // Default is Hidden
                .setSubtitleText("Damascus") // Default is Hidden
                .setTitleLogoUrl(profileimg) // Default is Hidden
                .setStoryClickListeners(object : StoryClickListeners {
                    override fun onDescriptionClickListener(position: Int) {
                        //your action
                    }

                    override fun onTitleIconClickListener(position: Int) {
                        //your action
                    }
                }) // Optional Listeners
                .build() // Must be called before calling show method
                .show()
        }

    }

}