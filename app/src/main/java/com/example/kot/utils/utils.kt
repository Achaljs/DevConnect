package com.example.kot.utils

import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri?, foldername:String, callback:(String?)->Unit) {
    var imageUrl: String? = null
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString()).putFile(uri!!)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                 callback(imageUrl)
            }
        }
}


fun changeInProgress(inProgress: Boolean,progressBar: ProgressBar,loginBtn: View) {
    if (inProgress) {
        progressBar.visibility = View.VISIBLE
        loginBtn.visibility = View.GONE
    } else {
        progressBar.visibility = View.GONE
        loginBtn.visibility = View.VISIBLE
    }
}
