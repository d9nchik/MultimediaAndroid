package com.example.videoplayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import androidx.activity.result.contract.ActivityResultContracts
import com.example.videoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaController: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        configureVideoView()
        binding.watch.setOnClickListener { newVideo() }
        // More about videos in device memory here: https://www.tutorialspoint.com/how-to-pick-an-image-from-an-image-gallery-on-android-using-kotlin
        // More about new intents here: https://medium.com/realm/startactivityforresult-is-deprecated-82888d149f5d
        binding.localVideo.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
            getResult.launch(gallery)
        }
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.videoView.setVideoURI(it.data?.data)
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