package com.example.kot

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.core.view.size
import com.example.kot.databinding.ActivityAddProjectBinding
import com.example.kot.modles.projects
import com.example.kot.utils.PROJECT_NODE
import com.example.kot.utils.USER_NODE
import com.google.android.play.integrity.internal.f
import com.google.android.play.integrity.internal.l
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.libaml.android.view.chip.ChipLayout
import org.checkerframework.checker.units.qual.C


class AddProject : AppCompatActivity() {

private lateinit var project: projects
    val binding by lazy {
        ActivityAddProjectBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar2.setNavigationOnClickListener {
            finish()
        }

project= projects()


        val countries = arrayOf("Java","Xml","Android","Go", "C#", "AWS Lambda", "Google Cloud Platform (GCP)", "Docker", "PostgreSQL", "Visual Studio Code", "Solidity (for smart contracts)", "JavaScript (including popular libraries and frameworks like React", "HTML", "Flask and Django (Python web frameworks)", "Jenkins", "Unity (C#)", "Python", "Rust", "Spring (Java)", "Ruby on Rails", "MongoDB", "Linux", "Arduino", "Apache", "SQL", "Swagger/OpenAPI", "Ethereum", "Unreal Engine (C++)", "Git", "GraphQL", "Windows", "Ansible", "Nginx", "Kotlin", "RESTful APIs", "PHP", "Terraform", "Microsoft Azure", "React", "IIS", "AWS", "WebAssembly", "Tomcat", "Microsoft SQL Server", "Swift", "MicroPython", "Oracle Database", "CSS", "Node.js", "IntelliJ IDEA", "CSS", "Sublime Text")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, countries)
        binding.chipText.adapter = adapter

var arr:ArrayList<String> = ArrayList();


        binding.chipText.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
           arr.add(selectedItem)

        }






        binding.done.setOnClickListener {

            if (validate(
                    binding.title.text.toString(),
                    binding.discription.text.toString(),
                    binding.editTextDate2.text.toString(),
                    binding.editTextDate3.text.toString(),
                    binding.Link.text.toString(),
                    arr
                )
            ) {

                project.Uid = Firebase.auth.currentUser?.uid.toString()
                project.title = binding.title.text.toString()
                project.discription = binding.discription.text.toString()
                project.startDate = binding.editTextDate2.text.toString()
                project.endDate = binding.editTextDate3.text.toString()
                project.link = binding.Link.text.toString()
                project.technologiesUsed = arr

                val progressDialog: ProgressDialog =
                    ProgressDialog.show(this, "Posting...", "Please,Wait!")
                Firebase.firestore.collection(PROJECT_NODE)
                    .document().set(project).addOnSuccessListener {

                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser?.uid.toString())
                            .collection(PROJECT_NODE).document().set(project).addOnSuccessListener {

                                progressDialog.dismiss()

                                startActivity(Intent(AddProject@ this, HomeActivity::class.java))

                            }


                    }
            }
        }


    }


    private fun validate(
                         title: String,
                         discription: String,
                         startDate: String,
                         endDate: String,
                         link: String,
                         technologiesUsed: ArrayList<String>):Boolean{

        if(title.equals("")){
            binding.title.error="Should not be empty"
            return false
        }
        if(discription.equals("")){
            binding.discription.error="Should not be empty"
            return false
        }
        if(startDate.equals("")){
            binding.editTextDate2.error="Should not be empty"
            return false
        }
        if(endDate.equals("")){
            binding.editTextDate3.error="Should not be empty"
            return false
        }

        if(!URLUtil.isValidUrl(link) || link.equals("")){
            binding.Link.error="Use valid url"
            return false
        }
       if(technologiesUsed.isEmpty()){
           binding.chipText.hint="add technology"
           return false
       }

       else {
           return true
        }
    }

}