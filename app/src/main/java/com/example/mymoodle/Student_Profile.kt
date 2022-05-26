package com.example.mymoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student_profile.*
class Student_Profile : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        auth = Firebase.auth

        db = Firebase.firestore
        getProfileData2()

    }
    private fun getProfileData2 (){
        db!!.collection("StudentsInfo").whereEqualTo("Email",auth.currentUser!!.email).get()
            .addOnSuccessListener { QuerySnapshot->
                txtEmail2.setText(QuerySnapshot.documents.get(0).get("Email").toString())
                txtPassword2.setText(QuerySnapshot.documents.get(0).get("Password").toString())
                PhoneNumber2.setText(QuerySnapshot.documents.get(0).get("PhoneNum").toString())
                dob2.setText(QuerySnapshot.documents.get(0).get("DOB").toString())
                Adress2.setText(QuerySnapshot.documents.get(0).get("Adress").toString())
            }.addOnFailureListener {Exception->

            }
    }


}