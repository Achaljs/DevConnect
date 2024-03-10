package com.example.kot

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.kot.databinding.ActivityAddStoryBinding
import com.example.kot.modles.storys
import com.example.kot.modles.userstories
import com.example.kot.utils.POST_FOLDER
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.STORY_FOLDER
import com.example.kot.utils.STORY_NODE
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.sql.Time

class AddStory : AppCompatActivity() {

private lateinit var story:storys
    val binding by lazy{

ActivityAddStoryBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        story=storys()
        var modle=ArrayList<userstories>()


        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)

        try {


            Firebase.firestore.collection(STORY_NODE)
                .document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener {

if (it.exists()) {
    modle = it.toObject<storys>()!!.stories
}


            }.addOnFailureListener {


            }
        }catch (e:Exception){

            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }


binding.done.setOnClickListener {



    if (imgUri==null){

    }else {
        val progressDialog: ProgressDialog = ProgressDialog.show(this,"Posting...","Please,Wait!")

        uploadImage(imgUri, STORY_FOLDER) {





story.storyBy=Firebase.auth.currentUser?.uid.toString()

if(modle.size==0){
    story.stories.add(userstories(it.toString(),System.currentTimeMillis().toString()))
}
            else{
                modle.add(userstories(it.toString(),System.currentTimeMillis().toString()))
                story.stories.addAll(modle)

            }




            var key = Firebase.firestore.collection(STORY_NODE).document(Firebase.auth.currentUser?.uid.toString());







            key.set(story).addOnCompleteListener { result ->


                if (result.isSuccessful) {


                        progressDialog.dismiss()
                        startActivity(
                            Intent(
                                applicationContext,
                                HomeActivity::class.java


                            )

                        )
                    finish()




                } else {
                    Toast.makeText(
                        this,
                        result.exception?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

}

    }
    var imgUri: Uri?=null
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                imgUri = data?.data
                binding.image.setImageURI(imgUri)


                binding.image.visibility = View.VISIBLE
            }
        }
}