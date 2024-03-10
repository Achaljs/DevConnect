package com.example.kot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kot.R
import com.example.kot.adapters.MYpostAdapter
import com.example.kot.adapters.MYprojectAdapter
import com.example.kot.adapters.ViewPagerAdapter
import com.example.kot.databinding.FragmentHomeBinding
import com.example.kot.databinding.FragmentProfileBinding
import com.example.kot.modles.posts
import com.example.kot.modles.projects
import com.example.kot.modles.users
import com.example.kot.utils.MY
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.PROJECT_NODE
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.detail
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class profile : Fragment() {

    private lateinit var postAdapter:MYpostAdapter
    private lateinit var projectAdapter:MYprojectAdapter

    private var postList=ArrayList<posts>()
    private var projectList=ArrayList<projects>()

 private lateinit var binding:FragmentProfileBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

binding=FragmentProfileBinding.inflate(LayoutInflater.from(requireContext()),container,false)


Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser?.uid.toString()).collection(
    detail).document(MY).get().addOnSuccessListener {
    var user=users()
    user= it.toObject<users>()!!

    Glide.with(requireContext()).load(user.image).placeholder(R.drawable.loding).into(binding.postProfileImage)
    binding.name.text= user.names
}




       postAdapter=MYpostAdapter(requireContext(),postList)
projectAdapter= MYprojectAdapter(requireContext(),projectList)
        binding.MypostRV.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
     binding.MypostRV.adapter=postAdapter
        binding.myprojectrv.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.myprojectrv.adapter=projectAdapter


        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser?.uid.toString()).collection(
            PROJECT_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<projects>()
            projectList.clear()
            for (i in it.documents){
                tempList.add(i.toObject<projects>()!!)
            }
            projectList.addAll(tempList)
            projectAdapter.notifyDataSetChanged()
        }

        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser?.uid.toString()).collection(
            POST_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<posts>()
            postList.clear()
            for (i in it.documents){
                tempList.add(i.toObject<posts>()!!)
            }
            postList.addAll(tempList)
            postAdapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {

    }
}