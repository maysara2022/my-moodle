package com.example.mymoodle

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_adding_files.*

class AddingFiles : AppCompatActivity() {
    val PDF: Int = 0
    val DOCX: Int = 1
    val AUDIO: Int = 2
    val VIDEO: Int = 3
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    private var db: FirebaseFirestore? = null
    var path: String? = null
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_files)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        imageView12.setOnClickListener {
            val k = Intent(this, Setting_Course::class.java)
            startActivity(k)
        }
        imageView11.setOnClickListener {
            val k = Intent(this, Lectuer_Profile::class.java)
            startActivity(k)
        }

        db = Firebase.firestore
        val id = System.currentTimeMillis()


        txtAddCourse.setOnClickListener {
            if (Course_Det.text.toString()
                    .isNotEmpty() && Course_Name.text.isNotEmpty() && Course_Tech.text.isNotEmpty()
            ) {

                addCourses(
                    id.toString(),
                    Course_Name.text.toString(),
                    Course_Det.text.toString(),
                    Course_Tech.text.toString(),
                    textView2.text.toString(),
                    pdfUrl.text.toString(),
                    wordurl.text.toString()


                )
                Toast.makeText(this, "Good added Course", Toast.LENGTH_LONG).show()

                Course_Det.text.clear()
                Course_Name.text.clear()
                Course_Tech.text.clear()

            } else {
                Toast.makeText(this, "please fill the forms", Toast.LENGTH_LONG).show()

            }
        }


        val pdfBtn = findViewById<View>(R.id.pdfBtn)
        val docxBtn = findViewById<View>(R.id.docxBtn)
        val videoBtn = findViewById<View>(R.id.videoBtn)

        mStorage = FirebaseStorage.getInstance().getReference("Uploads")

        pdfBtn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("application/pdf")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF)
        })

        docxBtn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select DOCX"), DOCX)
        })



        videoBtn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("video/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), VIDEO)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById<View>(R.id.uriTxt) as TextView
        if (resultCode == RESULT_OK) {
            if (requestCode == PDF) {
                uri = data!!.data!!
                uriTxt.text = uri.toString()
                uploadpdf()
            } else if (requestCode == DOCX) {
                uri = data!!.data!!
                uriTxt.text = uri.toString()
                uploadword()
            } else if (requestCode == AUDIO) {
                uri = data!!.data!!
                uriTxt.text = uri.toString()
                upload()
            } else if (requestCode == VIDEO) {
                uri = data!!.data!!
                uriTxt.text = uri.toString()
                upload()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun upload() {
        showDialog()
        var mReference = uri.lastPathSegment?.let { mStorage.child(it) }
        try {
            mReference!!.putFile(uri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    var url = taskSnapshot!!.task
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        path = uri.toString()
                        textView2.text = path.toString()
                        hideDialog()
                    }
                    val dwnTxt = findViewById<View>(R.id.dwnTxt) as TextView
                    dwnTxt.text = url.toString()
                    Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadpdf() {
        showDialog()
        var mReference = uri.lastPathSegment?.let { mStorage.child(it) }
        try {
            mReference!!.putFile(uri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    var url = taskSnapshot!!.task
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        path = uri.toString()
                        pdfUrl.text = path.toString()
                        hideDialog()
                    }
                    val dwnTxt = findViewById<View>(R.id.dwnTxt) as TextView
                    dwnTxt.text = url.toString()
                    Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadword() {
        showDialog()
        var mReference = uri.lastPathSegment?.let { mStorage.child(it) }
        try {
            mReference!!.putFile(uri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    var url = taskSnapshot!!.task
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        path = uri.toString()
                        wordurl.text = path.toString()
                        hideDialog()
                    }
                    val dwnTxt = findViewById<View>(R.id.dwnTxt) as TextView
                    dwnTxt.text = url.toString()
                    Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }


    private fun addCourses(
        id: String,
        CourseName: String,
        Details: String,
        CourseTech: String,
        Url: String,
        Url2: String,
        Url3: String
    ) {
        val CoursInfo = hashMapOf(
            "id" to id,
            "CourseName" to CourseName,
            "Details" to Details,
            "CourseTech" to CourseTech,
            "url" to Url,
            "url2" to Url2,
            "url3" to Url3

        )
        db!!.collection("CourseInfo").add(CoursInfo)

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

