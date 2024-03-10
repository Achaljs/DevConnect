package com.example.kot

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kot.adapters.MYpostAdapter
import com.example.kot.adapters.MYprojectAdapter
import com.example.kot.databinding.ActivityUserProfileBinding
import com.example.kot.modles.posts
import com.example.kot.modles.projects
import com.example.kot.modles.users
import com.example.kot.utils.Followers
import com.example.kot.utils.MY
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.PROJECT_NODE
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.detail
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.Objects

class UserProfile : AppCompatActivity() {

    private lateinit var postAdapter: MYpostAdapter
    private lateinit var projectAdapter: MYprojectAdapter

    private var postList=ArrayList<posts>()
    private var projectList=ArrayList<projects>()


    val binding by lazy {
        ActivityUserProfileBinding.inflate(LayoutInflater.from(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        val intent=getIntent()
      val UID: String? =  intent.getStringExtra("UID")





        Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(
            detail
        ).document(MY).get().addOnSuccessListener {
            var user= users()
            user= it.toObject<users>()!!

            Glide.with(this).load(user.image).placeholder(R.drawable.loding).into(binding.postProfileImage)
            binding.name.text= user.names
            binding.materialToolbar.title="@"+user.names
        }




        postAdapter=MYpostAdapter(this,postList)
        projectAdapter= MYprojectAdapter(this,projectList)
        binding.MypostRV.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.MypostRV.adapter=postAdapter
        binding.myprojectrv.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.myprojectrv.adapter=projectAdapter


        Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(
            PROJECT_NODE
        ).get().addOnSuccessListener {
            var tempList=ArrayList<projects>()
            projectList.clear()
            for (i in it.documents){
                tempList.add(i.toObject<projects>()!!)
            }
            projectList.addAll(tempList)
            projectAdapter.notifyDataSetChanged()
        }

        Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(
            POST_NODE
        ).get().addOnSuccessListener {
            var tempList=ArrayList<posts>()
            postList.clear()
            for (i in it.documents){
                tempList.add(i.toObject<posts>()!!)
            }
            postList.addAll(tempList)
            postAdapter.notifyDataSetChanged()
        }


var isFollow=false



val currentUser=Firebase.auth.currentUser?.uid.toString()
        Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(Followers).whereEqualTo("FollowerUID",currentUser).get().addOnSuccessListener {
            if(it.documents.size==0){
                isFollow=false
                binding.follow.text="Follow"
            }
            else{

                binding.follow.text="Following"
                isFollow=true
            }
        }


        binding.follow.setOnClickListener {


            if(isFollow){

                try {
                    Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(Followers).whereEqualTo("FollowerUID",currentUser).get().addOnSuccessListener {

                        Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(Followers).document(
                            it.documents.get(0).id).delete()
                            binding.follow.text="Follow"
                            Toast.makeText(this,"You Unfollwed this User",Toast.LENGTH_SHORT).show()

                    }

                }
                catch (e:Exception){

                }



            }
            else{

            var map = HashMap<String, String>()
            map.put("FollowerUID", currentUser)

                binding.follow.text="Following"


            Firebase.firestore.collection(USER_NODE).document(UID.toString()).collection(Followers)
                .document().set(map).addOnSuccessListener {

            }
        }

        }

    }
}