package com.example.mymoodle

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.ByteArrayOutputStream

class MainActivity2 : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    var storge: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var imageURI: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("maysara11")

        storge = Firebase.storage
        reference = storge!!.reference


        imageView5.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "PDF/*"

        }

        button2.setOnClickListener {

            showDialog()
            val bitmap = (imageView5.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()
            val childRef = imageRef.child(System.currentTimeMillis().toString() + ".png")
            var uploadTask = childRef.putBytes(data)

            uploadTask.

            addOnSuccessListener {
                childRef.downloadUrl.addOnSuccessListener { uri ->
                    hideDialog()
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ViewAll::class.java))


                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show()


            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            imageURI = data!!.data
            imageView5.setImageURI(imageURI)

        }
    }

    private fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Uploading ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hideDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }
}