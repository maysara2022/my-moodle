package com.example.mymoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_course.*
import kotlinx.android.synthetic.main.item2.*

class Edit_Course : AppCompatActivity() {
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_course)
        db = Firebase.firestore

        btn_Edit.setOnClickListener {
            CourseUpdate()
            Toast.makeText(applicationContext, "Course Edited Successfuly", Toast.LENGTH_LONG)
                .show()
        }
        btn_Delet.setOnClickListener {
            deletCourse()

            CE.text.clear()
            CE_Name.text.clear()
            CE_Tech.text.clear()
        }

        CE_Name.setText(intent.getStringExtra("CourseName").toString())
        CE_Tech.setText(intent.getStringExtra("CourseTech").toString())
        CE.setText(intent.getStringExtra("Details").toString())

    }

    private fun deletCourse() {
        db!!.collection("CourseInfo").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                document.toObject<CoursesMoodle>()
                if (document.get("id") ==
                    intent.getStringExtra("id")
                ) {
                    db!!.collection("CourseInfo").document(
                        document.id
                    ).delete()
                }
            }

        }

    }

    private fun CourseUpdate() {
        db!!.collection("CourseInfo").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                document.toObject<CoursesMoodle>()
                if (document.get("id") == intent.getStringExtra("id")) {
                    db!!.collection("CourseInfo").document(document.id)
                        .update("CourseName", CE_Name.text.toString())
                    db!!.collection("CourseInfo").document(document.id)
                        .update("CourseTech", CE_Tech.text.toString())
                    db!!.collection("CourseInfo").document(document.id)
                        .update("Details", CE.text.toString())


                }
            }
        }
    }




}