package com.example.kot

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.content.ContextCompat.startActivity
import com.example.kot.databinding.ActivitySignUpBinding
import com.example.kot.modles.users
import com.example.kot.utils.USER_NODE
import com.example.kot.utils.USER_PROFILE_FOlDER
import com.example.kot.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class signUp : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

   lateinit var user: users
var uri: Uri?=null
  private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

      if (it.resultCode == Activity.RESULT_OK) {
          val data = it.data
            uri = data?.data
          binding.profileImage.setImageURI(uri)
          binding.imageprogreesbar.visibility = View.INVISIBLE
          binding.profileImage.visibility = View.VISIBLE


           }
      }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user=users()
binding.btnSignUp.setOnClickListener{

    val name=binding.username.text.toString()
    val email=binding.Email.text.toString()
    val password=binding.Password.text.toString()

    if(validate(name,email,password)){
        changeInProgress(true,binding.signUpProgressBar,binding.btnSignUp)
          FirebaseAuth.getInstance().createUserWithEmailAndPassword(
              email,password
          ).addOnCompleteListener(this){


              if(it.isSuccessful){
                  user.names=binding.username.text.toString()
                  user.email=binding.Email.text.toString()
                  user.pass=binding.Password.text.toString()
         if(uri==null){
             Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).collection(
                 detail)
                 .document("MY")
                 .set(user).addOnCompleteListener{
                         result->
                     if(result.isSuccessful){
                         changeInProgress(false,binding.signUpProgressBar,binding.btnSignUp)
                         startActivity(Intent(applicationContext,LoginActivity::class.java))
                     }
                     else{
                         Toast.makeText(this, result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                     }
                 }
       }
        else {
    uploadImage(uri, USER_PROFILE_FOlDER) {
        user.image = it
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).collection(
            detail)
            .document("MY") .set(user).addOnCompleteListener{
                    result->
                if(result.isSuccessful){
                    changeInProgress(false,binding.signUpProgressBar,binding.btnSignUp)
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                }
                else{
                    Toast.makeText(this, result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }
             }
         }



              }
              else
              {
                  changeInProgress(false,binding.signUpProgressBar,binding.btnSignUp)
Toast.makeText(this, it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
              }
          }

    }


}
binding.addImage.setOnClickListener{

    val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    launcher.launch(pickImg)
}


        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(signUp@this,LoginActivity::class.java))
        }


    }

    fun validate(name:String,email:String,password:String): Boolean {

        if(name.equals("")){
            binding.username.error = "Enter the Username"
            return false
        }
        if(email.equals("")){
            binding.Email.error = "Enter email"
            return false
        }
        if(password.equals("")){
            binding.Password.error = "Enter the password"
            return false
        }

        else{
            return true
        }

    }



}