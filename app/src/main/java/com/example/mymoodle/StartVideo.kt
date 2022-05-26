package com.example.mymoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.activity_course_inf.*

class StartVideo : AppCompatActivity(), Player.Listener {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_video)
        progressBar = findViewById(R.id.progressBar)
        playerView = findViewById(R.id.video_view)

        setupPlayer()
        setMP4File(intent.getStringExtra("url").toString())




    }

    private fun setMP4File(url_v: String) {
        val mediaItem = MediaItem.fromUri(url_v)
        player.setMediaItem(mediaItem)
        player.prepare()


    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        player.addListener(this)
    }

    // handle loading
    override fun onPlaybackStateChanged(state: Int) {
        when (state) {
            Player.STATE_BUFFERING -> {
                progressBar.visibility = View.VISIBLE

            }

            Player.STATE_READY -> {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("SeekTime", player.currentPosition)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}