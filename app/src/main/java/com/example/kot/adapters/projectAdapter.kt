package com.example.kot.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot.MainActivity2
import com.example.kot.R
import com.example.kot.UserProfile
import com.example.kot.databinding.ProjectrvlayoutBinding
import com.example.kot.modles.projects
import com.example.kot.modles.users
import com.example.kot.utils.MY
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.detail
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class projectAdapter(var context: Context, var projectList: ArrayList<projects>) : RecyclerView.Adapter<projectAdapter.viewHolder>(){


    inner class viewHolder(var binding:ProjectrvlayoutBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       var view = ProjectrvlayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {

        return projectList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        try {

            Firebase.firestore.collection(USER_NODE).document(projectList.get(position).Uid).collection(
               detail).document("MY").get()
                .addOnSuccessListener {
                    var user = it.toObject<users>()

                    Glide.with(context).load(user!!.image).placeholder(R.drawable.loding)
                        .into(holder.binding.postProfileimage)
                    holder.binding.postUser.text = user.names


                }.addOnFailureListener {
                    Toast.makeText(context,it.localizedMessage, Toast.LENGTH_SHORT).show()
                }

        }
        catch (e:Exception){
            Toast.makeText(context,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }

        holder.binding.title.text=projectList.get(position).title
        holder.binding.discription.text=projectList.get(position).discription
        holder.binding.date.text=projectList.get(position).startDate+" - "+projectList.get(position).endDate
        holder.binding.link.text=projectList.get(position).link

        holder.binding.link.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        for (i in  0..projectList.get(position).technologiesUsed.size-1)
        {
            val tv_dynamic = TextView(context)
            tv_dynamic.textSize = 18f
            tv_dynamic.text = projectList.get(position).technologiesUsed.get(i)
            tv_dynamic.setBackgroundResource(R.drawable.backgroundpost)
            tv_dynamic.setPadding(15,5,15,5)
            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10, 0, 10, 0)
            tv_dynamic.setLayoutParams(params)

            // add TextView to LinearLayout
            holder.binding.techLayout.addView(tv_dynamic)
        }


        holder.binding.postUser.setOnClickListener {
            if (!projectList.get(position).Uid.toString().equals(Firebase.auth.currentUser?.uid.toString())) {
                val intent = Intent(context, UserProfile::class.java)
                intent.putExtra("UID", projectList.get(position).Uid)
                context.startActivity(intent)
            }
        }


    }



}