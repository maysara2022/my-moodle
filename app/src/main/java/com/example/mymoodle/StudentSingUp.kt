package com.example.mymoodle

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student_sing_up.*

class StudentSingUp : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_sing_up)

        auth = Firebase.auth
        db = Firebase.firestore
        firebaseAnalytics = Firebase.analytics


        var v1 = findViewById<TextView>(R.id.SingUpStudent)
        v1.setOnClickListener {
            val mainBundle = Bundle()
            mainBundle.putString("Logup", "Button")
            firebaseAnalytics.logEvent("new_SingUp", Bundle())
        }

        val id = System.currentTimeMillis()

        GoToSingIn.setOnClickListener {
            startActivity(Intent(this, StudentSingIn::class.java))
            val mainBundle = Bundle()
            mainBundle.putString("Logup", "Button")
            firebaseAnalytics.logEvent("Have_An_Account", Bundle())
        }


        SingUpStudent.setOnClickListener {
            if (rb_StudentSN.isChecked) {
                createnewStudent(txtEmail.text.toString(), txtPassword.text.toString())
                AddStudent(
                    id.toString(),
                    First.text.toString(),
                    Midlle.text.toString(),
                    Family.text.toString(),
                    txtEmail.text.toString(),
                    txtPassword.text.toString(),
                    dob.text.toString(),
                    Adress.text.toString(),
                    PhoneNumber.text.toString()
                )

            } else if (rb_LectulerSN.isChecked) {

                createnewLectuer(txtEmail.text.toString(), txtPassword.text.toString())
                AddLectuler(
                    id.toString(),
                    First.text.toString(),
                    Midlle.text.toString(),
                    Family.text.toString(),
                    txtEmail.text.toString(),
                    txtPassword.text.toString(),
                    dob.text.toString(),
                    Adress.text.toString(),
                    PhoneNumber.text.toString()
                )
            }


        }
    }

    private fun createnewStudent(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "good ", Toast.LENGTH_LONG).show()
                val user = auth.currentUser
                val i = Intent(this, StudentSingIn::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "bad ", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun createnewLectuer(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "good ", Toast.LENGTH_LONG).show()
                val user = auth.currentUser
                val i = Intent(this, StudentSingIn::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "bad ", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun AddStudent(
        id: String, Fname: String, Mname: String, Sname: String, Email: String, Password: String,
        DOB: String, Adress: String, PhoneNum: String
    ) {
        val Students = hashMapOf(
            "id" to id,
            "Fname" to Fname,
            "Mname" to Mname,
            "Sname" to Sname,
            "Email" to Email,
            "Password" to Password,
            "DOB" to DOB,
            "Adress" to Adress,
            "PhoneNum" to PhoneNum
        )

        db!!.collection("StudentsInfo").add(Students)

    }

    private fun AddLectuler(
        id: String, Fname: String, Mname: String, Sname: String, Email: String, Password: String,
        DOB: String, Adress: String, PhoneNum: String
    ) {
        val Lectulers = hashMapOf(
            "id" to id,
            "Ffname" to Fname,
            "Mmname" to Mname,
            "Ssname" to Sname,
            "Eemail" to Email,
            "Ppassword" to Password,
            "DdOB" to DOB,
            "Aadress" to Adress,
            "PphoneNum" to PhoneNum
        )

        db!!.collection("LectulerInfo").add(Lectulers)

    }

}

