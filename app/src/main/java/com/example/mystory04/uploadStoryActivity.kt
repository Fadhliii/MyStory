package com.example.mystory04


import android.net.Uri
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mystory04.databinding.ActivityUploadStoryBinding
import getImageUri
import uriToFile


class uploadStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadStoryBinding
    private var currentImageUri: Uri? = null // current image uri is null initially
    // so that we can check if user has selected image or not
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //
        binding.imageView.setOnClickListener {
            //open dialog if user click on imageview
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
        }

    }

    // give alert dialog if user click on imageview
    private fun alertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Upload Image Via Gallery") { dialog, _ ->
            startGallery() // start gallery
            dialog.dismiss()
        }
        builder.setNegativeButton("Upload Image Via Camera") { dialog, _ ->
            startCamera() // start camera
            dialog.dismiss()
        }
        val dialog = builder.create() // create dialog
        dialog.show() // show dialog
    }

    // launch gallery
    private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) { // if uri is not null then set currentImageUri to uri
            currentImageUri = uri
            showImage() // show image to imageview
        }
        else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    // show image to imageview
    private fun showImage() {
        val uri = currentImageUri
        if (uri != null) { // if uri is not null then set image to imageview using uri path
            binding.imageView.setImageURI(uri)
            Log.d("Image URI", "No media selected")
        }
    }

    // start gallery from launcherGallery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    // start camera from launcherIntentCamera
    private fun startCamera() {
        currentImageUri = getImageUri(this) // get image uri
        launcherIntentCamera.launch(currentImageUri!!) // launch camera intent with image uri
    }

    // launch camera intent
    private val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun uploadImage(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image Path", "showImage : ${imageFile.path}")
            showLoading(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
       binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}