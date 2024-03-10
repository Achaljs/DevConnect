package com.example.kot

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kot.databinding.ActivityMain2Binding
import com.example.kot.modles.posts
import com.example.kot.utils.POST_FOLDER
import com.example.kot.utils.POST_NODE
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity2 : AppCompatActivity() {

    val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post:posts=posts()

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {


            finish()
        }

        binding.addImage.setOnClickListener {


            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)

        }

        binding.done.setOnClickListener {

            if (binding.checkBox.isChecked) {

                if (imgUri == null) {
                    Toast.makeText(this,"Add an image to post",Toast.LENGTH_SHORT).show()
                }

                else {
val progressDialog:ProgressDialog= ProgressDialog.show(this,"Posting...","Please,Wait!")


                         post.uid= Firebase.auth.currentUser!!.uid

                    post.caption=binding.caption.text.toString()
                    post.time=System.currentTimeMillis().toString()




                    uploadImage(imgUri, POST_FOLDER) {
                        post.postImage = it.toString()

                       var key =  Firebase.firestore.collection(POST_NODE).document();
                     var   UniqueID = key.getId();


                        post.postID= UniqueID;



                            key.set(post).addOnCompleteListener { result ->


                                if (result.isSuccessful) {

                                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser?.uid.toString()).collection(
                                        POST_NODE).document(UniqueID).set(post).addOnSuccessListener {
                                        progressDialog.dismiss()
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                HomeActivity::class.java



                                            )
                                        )

                                    }


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

                else
                {
                    Toast.makeText(this, "check the box, First", Toast.LENGTH_SHORT).show()
                }
            }

        }


    private fun getBitmapFromViewUsingCanvas(view: View): Bitmap? {
        // Create a new Bitmap object with the desired width and height
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

        // Create a new Canvas object using the Bitmap
        val canvas = Canvas(bitmap)

        // Draw the View into the Canvas
        view.draw(canvas)

        // Return the resulting Bitmap
        return bitmap
    }


    var imgUri: Uri?=null
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                imgUri = data?.data
                binding.postImage.setImageURI(imgUri)


                binding.postImage.visibility = View.VISIBLE
            }
        }



}