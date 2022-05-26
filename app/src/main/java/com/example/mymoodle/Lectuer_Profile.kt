package com.example.mymoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.lectuer_profile.*

class Lectuer_Profile : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lectuer_profile)
        auth = Firebase.auth

        db = Firebase.firestore
        getProfileData ()

    }
    private fun getProfileData (){
        db!!.collection("LectulerInfo").whereEqualTo("Eemail",auth.currentUser!!.email).get()
            .addOnSuccessListener { QuerySnapshot->
                txtEmail22.setText(QuerySnapshot.documents.get(0).get("Eemail").toString())
                txtPassword22.setText(QuerySnapshot.documents.get(0).get("Ppassword").toString())
                PhoneNumber22.setText(QuerySnapshot.documents.get(0).get("PphoneNum").toString())
                dob22.setText(QuerySnapshot.documents.get(0).get("DdOB").toString())
                Adress22.setText(QuerySnapshot.documents.get(0).get("Aadress").toString())
            }.addOnFailureListener {Exception->

            }
    }


}