package com.example.mymoodle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student_sing_in.*

class StudentSingIn : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_sing_in)
        firebaseAnalytics = Firebase.analytics

        auth = Firebase.auth
        db = Firebase.firestore


        GoToSingUp.setOnClickListener {
            val o = Intent(this, StudentSingUp::class.java)
            startActivity(o)
        }

        SingINStudent.setOnClickListener {
            LogInAccountstd(txtEmail_INstd.text.toString(), txtPassword_InStd.text.toString())
            val mainBundle = Bundle()
            mainBundle.putString("Logup", "Button")
            firebaseAnalytics.logEvent("new_Login", Bundle())
        }
    }

    private fun LogInAccountstd(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    db!!.collection("StudentsInfo")
                        .whereEqualTo("Email", txtEmail_INstd.text.toString())
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                Toast.makeText(this, "Student", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, ViewAll::class.java))

                            }
                        }.addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                db!!.collection("LectulerInfo")
                                    .whereEqualTo("Eemail", txtEmail_INstd.text.toString())
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            Toast.makeText(this, "Techer", Toast.LENGTH_LONG).show()
                                            startActivity(Intent(this, AddingFiles::class.java))

                                        }
                                    }
                            }
                        }
                } else {
                    db!!.collection("LectulerInfo")
                        .whereEqualTo("Eemail", txtEmail_INstd.text.toString())
                        .get()
                    Toast.makeText(this, "bad", Toast.LENGTH_LONG).show()

                }
            }
    }
}