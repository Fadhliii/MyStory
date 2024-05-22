package com.example.mystory04


import android.net.Uri
import android.os.Bundle
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mystory04.Response.AddNewStoryResponse
import com.example.mystory04.databinding.ActivityUploadStoryBinding
import com.google.gson.Gson
import getImageUri
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import reduceFileImage
import retrofit2.HttpException
import uriToFile


class UploadStoryActivity : AppCompatActivity() {
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
        binding.progressBar.visibility = View.GONE
        binding.Upload.setOnClickListener {
            uploadImage()
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
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageView.setImageURI(it)
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

    //    private fun showLoading(isLoading: Boolean) {
    //        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    //    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = "Ini adalah deksripsi gambar"

            //            showLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
            )
            // lifecycleScope is used to launch a coroutine in the lifecycle of the activity
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.addNewStory(multipartBody, requestBody)
                    Toast.makeText(
                            this@UploadStoryActivity,
                            "Image uploaded successfully: ${successResponse.message}",
                            Toast.LENGTH_SHORT).show()

                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, AddNewStoryResponse::class.java)
                    Toast.makeText(
                            this@UploadStoryActivity,
                            "Failed to upload image: ${errorResponse.message}",
                            Toast.LENGTH_SHORT).show()
                }
            }


            //            // Get user input from TextInputEditText fields
            //            val inputDesc = binding.textInputLayout2.editText?.text.toString()
            //            val inputTitle = binding.textInputLayout1.editText?.text.toString()
            //
            //            // Convert user input to RequestBody
            //            val description = RequestBody.create(MultipartBody.FORM, inputDesc)
            //            val title = RequestBody.create(MultipartBody.FORM, inputTitle)
        } ?: showToast(getString(R.string.empty_image_warning))
    }
}