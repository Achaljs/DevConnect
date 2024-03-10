package com.example.kot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kot.adapters.postAdapter
import com.example.kot.adapters.projectAdapter
import com.example.kot.databinding.FragmentProjectBinding
import com.example.kot.modles.posts
import com.example.kot.modles.projects
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.PROJECT_NODE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class projects : Fragment() {
    private var projectList = ArrayList<projects>()
    private lateinit var projectAdapter: projectAdapter


    lateinit var binding:FragmentProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentProjectBinding.inflate(LayoutInflater.from(requireContext()), container, false)


        projectAdapter= projectAdapter(requireContext(),projectList)
        binding.recyclerView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        binding.recyclerView.adapter=projectAdapter

        Firebase.firestore.collection(PROJECT_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<projects>()
            projectList.clear()
            for (i in it.documents){
                tempList.add(i.toObject<projects>()!!)
            }
            projectList.addAll(tempList)
            projectAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {


    }
}


