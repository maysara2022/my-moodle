package com.example.mymoodle

import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_adding_files.*
import kotlinx.android.synthetic.main.activity_course_inf.*

class CourseInf : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    val PDF: Int = 0
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    var path: String? = null
    lateinit var progressDialog: ProgressDialog
    private lateinit var textView: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_inf)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        db = Firebase.firestore

        textView = findViewById(R.id.urllll)
        textView2 = findViewById(R.id.pdftxt)
        textView3 = findViewById(R.id.url2222)
        textView4 = findViewById(R.id.wordtxt)

        textView2.setOnClickListener {
            val url = textView.text.toString()
            val reqest = DownloadManager.Request(Uri.parse(url))
                .setTitle("File")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDescription("Downloading >>")
                .setAllowedOverMetered(true)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(reqest)

        }
        textView4.setOnClickListener {
            val url = textView3.text.toString()
            val reqest = DownloadManager.Request(Uri.parse(url))
                .setTitle("File")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDescription("Downloading >>")
                .setAllowedOverMetered(true)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(reqest)

        }


        C_name.setText(intent.getStringExtra("CourseName").toString())
        C_Tech.setText(intent.getStringExtra("CourseTech").toString())
        C_det.setText(intent.getStringExtra("Details").toString())
        pdftxt.setText(intent.getStringExtra("Details").toString())
        wordtxt.setText(intent.getStringExtra("Details").toString())
        urllll.setText(intent.getStringExtra("url2").toString())
        url2222.setText(intent.getStringExtra("url3").toString())

        gotovideoList.setOnClickListener {
            val m = Intent(this, ViewVideo::class.java)
            startActivity(m)

        }
        My_Courses.setOnClickListener {
            val m = Intent(this, ViewAll::class.java)
            startActivity(m)

        }

        val pdfBtn = findViewById<View>(R.id.C_det)
        mStorage = FirebaseStorage.getInstance().getReference("assy")



        pdfBtn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("application/pdf")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF)
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById<View>(R.id.uriTxt2) as TextView
        if (resultCode == RESULT_OK) {
            if (requestCode == PDF) {
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
            mReference!!.putFile(uri).addOnSuccessListener {

                    taskSnapshot: UploadTask.TaskSnapshot? ->
                var url = taskSnapshot!!.task
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    path = uri.toString()
                    hideDialog()
                }
                val dwnTxt2 = findViewById<View>(R.id.dwnTxt2) as TextView
                dwnTxt2.text = url.toString()


                Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()

            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
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