package com.example.video28_11_22

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.MediaController
import com.example.video28_11_22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //Parte 2
    lateinit var mediaController: MediaController
    //Parte 3
    var posicion = 0 //Esta variable recuerda la posicion en la que estamos al girar
    var rutaVideo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaController = MediaController(this)
        setListeners()
        //iniciar()
    }



    private fun setListeners() {
        binding.btnPlay.setOnClickListener {
            reproducirVideo()
        }
    }

    private fun reproducirVideo() {
        //Generamos aleatorio entre 0 y 1
        var aleatorio = (0 .. 1).random()
        var idVideo = 0
        when (aleatorio) {
            0 -> {
                idVideo = R.raw.video1
            }
            1 -> {
                idVideo = R.raw.video2
            }
        }
        rutaVideo = "android.resource://"+packageName+"/$idVideo"
        var uri = Uri.parse(rutaVideo)
        try {
            binding.videoView.setVideoURI(uri)
            binding.videoView.requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Empezamosel video
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        //binding.videoView.start()
        if (posicion == 0) {
            binding.videoView.start()
        } else {
            binding.videoView.pause()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outPersistentState.putInt("POSICION", binding.videoView.currentPosition)
        binding.videoView.pause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        posicion = savedInstanceState.getInt("POSICION")
        binding.videoView.seekTo(posicion)
    }
}