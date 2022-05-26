package com.example.mymoodle

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        editText = findViewById(R.id.tilLink)
        button = findViewById(R.id.btnLoad)
        button.setOnClickListener {
            val url = editText.text.toString()
            val reqest = DownloadManager.Request(Uri.parse(url))
                .setTitle("File")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDescription("Downloading >>")
                .setAllowedOverMetered(true)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(reqest)

        }

    }
}