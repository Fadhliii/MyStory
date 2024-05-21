package com.example.mystory04


import android.net.Uri
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mystory04.databinding.ActivityUploadStoryBinding
import getImageUri

//Gallery button
//Upload button
//Camera Button


class uploadStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadStoryBinding
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //
        binding.imageView.setOnClickListener {
            //open file picker
            alertDialog(this, "Choose Image", "Choose from Gallery or Camera")
        }
        // ! Gallery
        binding.btnGallery.setOnClickListener() {
            startGallery()
        }
        // ! Camera
        binding.btnCamera.setOnClickListener() {
            startCamera()
            //open camera
            Toast.makeText(this, "camera need to open", Toast.LENGTH_SHORT).show()
        }

    }

    private fun alertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Upload Image Via Gallery") { dialog, _ ->
            startGallery()
            dialog.dismiss()
        }
        builder.setNegativeButton("Upload Image Via Camera") { dialog, _ ->
            startCamera()
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    // launch gallery
    private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
        else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    // show image to imageview
    private fun showImage() {
        val uri = currentImageUri
        if (uri != null) {
            binding.imageView.setImageURI(uri)
            Log.d("Image URI", "No media selected")
        }
    }

    // start gallery from launcherGallery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }
    private val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }
}