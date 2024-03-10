package com.example.kot.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kot.AddStory
import com.example.kot.R
import com.example.kot.adapters.StoryAdapter
import com.example.kot.adapters.postAdapter
import com.example.kot.databinding.FragmentHomeBinding
import com.example.kot.databinding.PostBinding
import com.example.kot.modles.posts
import com.example.kot.modles.storys
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.STORY_NODE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class home : Fragment() {

    private var postlist = ArrayList<posts>()
private lateinit var postAdapter: postAdapter



    private var storylist = ArrayList<storys>()
    private lateinit var storyAdapter: StoryAdapter

      private  lateinit var binding:FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        postAdapter=postAdapter(requireContext(),postlist)
binding.PostRV.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        binding.PostRV.adapter=postAdapter

Firebase.firestore.collection(POST_NODE).get().addOnSuccessListener {


var tempList=ArrayList<posts>()
postlist.clear()
    for (i in it.documents){
       var a=i.toObject<posts>()!!
        a.postID=i.id
tempList.add(a)
    }
postlist.addAll(tempList)
    postAdapter.notifyDataSetChanged()
}


        //Story REcylerview

        storyAdapter=StoryAdapter(requireContext(),storylist)
        binding.storyrv.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.storyrv.isNestedScrollingEnabled=false
        binding.storyrv.adapter=storyAdapter

        Firebase.firestore.collection(STORY_NODE).get().addOnSuccessListener {


            var tempList=ArrayList<storys>()
            storylist.clear()
            for (i in it.documents){
                var a=i.toObject<storys>()!!

                tempList.add(a)
            }
            storylist.addAll(tempList)
            storyAdapter.notifyDataSetChanged()
        }


        binding.addStory.setOnClickListener {

            startActivity(Intent(requireContext(),AddStory::class.java))
        }

        return binding.root
    }

    companion object {


    }
}