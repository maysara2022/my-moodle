package com.example.mymoodle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_all.*
import kotlinx.android.synthetic.main.item.view.*

class ViewAll : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    var adabter: FirestoreRecyclerAdapter<CoursesMoodle, MyViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        db = Firebase.firestore

        getAllCourses()

        Profile.setOnClickListener {
            val b = Intent(this,Student_Profile::class.java)
            startActivity(b)
        }

    }

    private fun getAllCourses() {
        val query = db!!.collection("CourseInfo")
        var option = FirestoreRecyclerOptions.Builder<CoursesMoodle>()
            .setQuery(query, CoursesMoodle::class.java).build()
        adabter = object : FirestoreRecyclerAdapter<CoursesMoodle, MyViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view =
                    LayoutInflater.from(this@ViewAll).inflate(R.layout.item, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: MyViewHolder,
                position: Int,
                model: CoursesMoodle
            ) {

                holder.CourseName.text = model.CourseName
                holder.CourseLectuler.text = model.CourseTech
                holder.CourseInfo.setOnClickListener {
                    intent(
                        model.id,
                        model.CourseName,
                        model.CourseTech,
                        model.Details,
                        model.url2,
                        model.url3


                    )

                }


            }
        }
        tvCourses.layoutManager = LinearLayoutManager(this)
        tvCourses.adapter = adabter

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var CourseName = view.txt_CourseName
        var CourseLectuler = view.txt_CourseLec
        var CourseInfo = view.View_Info


    }

    override fun onStart() {
        super.onStart()
        adabter!!.startListening()

    }

    override fun onStop() {
        super.onStop()
        adabter!!.stopListening()
    }


    fun intent(
        id: String,
        Name_Course: String,
        Name_Tech: String,
        Dit: String,
        pdf:String,
        word:String

        ) {
        val i = Intent(this, CourseInf::class.java)
        i.putExtra("id", id)
        i.putExtra("CourseName", Name_Course)
        i.putExtra("CourseTech", Name_Tech)
        i.putExtra("Details", Dit)
        i.putExtra("url2", pdf)
        i.putExtra("url3", word)
        startActivity(i)



    }


}