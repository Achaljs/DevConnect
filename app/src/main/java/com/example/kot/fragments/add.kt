package com.example.kot.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kot.AddProject
import com.example.kot.MainActivity2
import com.example.kot.R
import com.example.kot.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class add : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater, container, false)


        binding.post.setOnClickListener {
                  activity?.startActivity(Intent(requireContext(),MainActivity2::class.java))
              }
        binding.project.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),AddProject::class.java))
        }
        binding.story.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),MainActivity2::class.java))
        }
        return binding.root
    }

    companion object {

            }
    }
