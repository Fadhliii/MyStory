import java.util.Locale
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.example.mystory04.BuildConfig
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

// getimageuri funcion to get image uri for camera and gallery and save image in external storage
fun getImageUri(context: Context): Uri {
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg") // file name
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg") // file type
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/") // file path to save
        }
        uri = context.contentResolver.insert( // insert image in external storage
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // external content uri
                contentValues
        )
    }
    return uri ?: getImageUriForPreQ(context) // return uri
}

// this function is used to get image uri for pre Q devices. pre Q devices are those devices which are below android 10
private fun getImageUriForPreQ(context: Context): Uri {
    val filesDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // get external storage directory
    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg") // create image file
    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir() // create directory if not exists
    return FileProvider.getUriForFile( // return uri for file provider to access file in external storage
            context,
            "${BuildConfig.APPLICATION_ID}.provider",
            imageFile // image file to access uri for file provider
    )
}

// this function is used to create custom temp file to save image in external storage for cam and gal
fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

// this function is to convert uri to file so it can be upload to server using retrofit
    fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
}
