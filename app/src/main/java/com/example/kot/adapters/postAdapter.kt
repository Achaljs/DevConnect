package com.example.kot.adapters

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot.R
import com.example.kot.UserProfile
import com.example.kot.databinding.PostBinding
import com.example.kot.modles.posts
import com.example.kot.modles.users
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.detail
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.play.core.integrity.p
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class postAdapter(var context:Context,var postList: ArrayList<posts>):RecyclerView.Adapter<postAdapter.myHolder>(){


    inner class myHolder(var binding:PostBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        var binding=PostBinding.inflate(LayoutInflater.from(context),parent,false)
        return myHolder(binding)

    }

    override fun getItemCount(): Int {
        return postList.size

    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {

        try {

            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).collection(
                detail).document("MY").get()
                .addOnSuccessListener {
                    var user = it.toObject<users>()

                    Glide.with(context).load(user!!.image).placeholder(R.drawable.loding)
                        .into(holder.binding.postProfileimage)
                    holder.binding.postUser.text = user.names
                    holder.binding.postCaption.text="@"+user.names
                    holder.binding.realcaption.text= "@"+user.names+"  "+postList.get(position).caption





                }.addOnFailureListener {
                    Toast.makeText(context,it.localizedMessage,Toast.LENGTH_SHORT).show()
                }

        }
        catch (e:Exception){
Toast.makeText(context,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }

        val text = TimeAgo.using(postList.get(position).time.toLong())
        holder.binding.time.text=text
        Glide.with(context).load(postList.get(position).postImage).placeholder(R.drawable.loding).into(holder.binding.mainImage)








        //Like Code Started


val currentUser=Firebase.auth.currentUser?.uid.toString()
        var isLiked=false
        Firebase.firestore.collection(POST_NODE).document(postList.get(position).postID).get().addOnSuccessListener {

            var post=posts()
            post= it.toObject<posts>()!!


            if (post.Likedby.contains(currentUser)){
                holder.binding.like.setImageResource(R.drawable.heartfill)
                isLiked=true
            }
            else{
                isLiked=false
            }
        }



        holder.binding.like.setOnClickListener {

            if(isLiked){
                holder.binding.like.setImageResource(R.drawable.heart)
                postList.get(position).Likedby.remove(currentUser)
                Firebase.firestore.collection(POST_NODE).document(postList.get(position).postID).update("likedby",postList.get(position).Likedby).addOnSuccessListener {
                    Firebase.firestore.collection(USER_NODE)
                        .document(postList.get(position).uid).collection(
                            POST_NODE
                        ).document(postList.get(position).postID).update("likedby",postList.get(position).Likedby).addOnSuccessListener {


                        isLiked=false
                    }
                }




            }
            else {
                holder.binding.like.setImageResource(R.drawable.heartfill)

                postList.get(position).Likedby.add(Firebase.auth.currentUser?.uid.toString())
                var modle = postList.get(position)


                Firebase.firestore.collection(POST_NODE).document(postList.get(position).postID)
                    .set(modle).addOnSuccessListener {

                        Firebase.firestore.collection(USER_NODE)
                            .document(postList.get(position).uid).collection(
                            POST_NODE
                        ).document(postList.get(position).postID)
                            .set(modle).addOnSuccessListener {



                                isLiked = true
                            }

                    }


            }
}


        //Like code Ended





//comment Code Statred






        //Comment code ended






        holder.binding.postUser.setOnClickListener {
            if (!postList.get(position).uid.toString().equals(Firebase.auth.currentUser?.uid.toString())) {
                val intent = Intent(context, UserProfile::class.java)
                intent.putExtra("UID", postList.get(position).uid)
                context.startActivity(intent)
            }
        }

    }

}