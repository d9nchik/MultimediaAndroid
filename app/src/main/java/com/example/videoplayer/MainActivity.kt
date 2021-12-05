package com.example.videoplayer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import com.example.videoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaController: MediaController? = null
    private val pickVideo = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        configureVideoView()
        binding.watch.setOnClickListener { newVideo() }
        binding.localVideo.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickVideo)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickVideo) {
            val Uri = data?.data
            binding.videoView.setVideoURI(Uri)
            binding.videoView.start()
        }
    }

    private fun configureVideoView() {
        binding.videoView.setVideoURI(
            Uri.parse(
                "android.resource://"
                        + packageName + "/" + R.raw.movie
            )
        )
        binding.videoView.start()
        mediaController = MediaController(this)
        mediaController?.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)
    }

    private fun newVideo() {
        if (binding.urlInput.text.isNotEmpty()) {
            binding.videoView.setVideoURI(Uri.parse(binding.urlInput.text.toString()))
            binding.videoView.start()
        }
    }

}