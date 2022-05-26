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
import kotlinx.android.synthetic.main.activity_setting_course.*
import kotlinx.android.synthetic.main.activity_view_all.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item2.view.*
import kotlinx.android.synthetic.main.video_item.view.*

class Setting_Course : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    var adabter: FirestoreRecyclerAdapter<CoursesMoodle, MyViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_course)
        db = Firebase.firestore
        getAllCourses()
    }


    private fun getAllCourses() {
        val query = db!!.collection("CourseInfo")
        var option = FirestoreRecyclerOptions.Builder<CoursesMoodle>()
            .setQuery(query, CoursesMoodle::class.java).build()
        adabter = object : FirestoreRecyclerAdapter<CoursesMoodle, MyViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view =
                    LayoutInflater.from(this@Setting_Course).inflate(R.layout.item2, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: MyViewHolder,
                position: Int,
                model: CoursesMoodle
            ) {

                holder.CourseName.text = model.CourseName
                holder.CourseInfo.setOnClickListener {
                    intent(
                        model.id,
                        model.CourseName,
                        model.CourseTech,
                        model.Details


                    )

                }


            }
        }
        tvSetting.layoutManager = LinearLayoutManager(this)
        tvSetting.adapter = adabter

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var CourseName = view.txtCName
        var CourseInfo = view.Edit


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


        ) {
        val i = Intent(this, Edit_Course::class.java)
        i.putExtra("id", id)
        i.putExtra("CourseName", Name_Course)
        i.putExtra("CourseTech", Name_Tech)
        i.putExtra("Details", Dit)
        startActivity(i)
        finish()

    }

}