package com.ibrahim.sawitpro.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ibrahim.sawitpro.R
import com.ibrahim.sawitpro.databinding.ActivityMainBinding
import com.ibrahim.sawitpro.presentation.detail.DetailActivity
import com.ibrahim.sawitpro.utils.Utils
import com.ibrahim.sawitpro.utils.showToast
import com.ibrahim.sawitpro.utils.subscribe
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    // region initialization

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val MAX_SIZE_IMAGE_PROFILE = 2048 // KBytes = 2MB
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private val viewModel: OcrViewModel by inject()
    private lateinit var binding: ActivityMainBinding

    private lateinit var fileImage: File

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                callApiUpload()
            }
        }

    // endregion

    // region setup action & setup observe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabCamera.setOnClickListener {
            if (allPermissionsGranted()) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }
        viewModel.imageResult.observe(this) { result ->
            result.subscribe(
                doOnSuccess = { response ->
                    showLoading(false)
                    showToast(getString(R.string.label_success))
                    val textResult = response.payload?.first()?.parsedText.orEmpty()
                    navigateToDetailScreen(textResult)
                },
                doOnError = { error ->
                    showLoading(false)
                    showToast(error.exception?.message.orEmpty())
                },
                doOnLoading = {
                    showLoading(true)
                }
            )
        }
    }

    // endregion

    private fun navigateToDetailScreen(textResult: String) {
        DetailActivity.start(
            context = this@MainActivity,
            textResult = textResult,
            imageUri = getCameraUri(false).toString()
        )
    }

    private fun showLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun callApiUpload() {
        val maxSize = MAX_SIZE_IMAGE_PROFILE
        val fileSizeInKb = fileImage.length() / 1024
        if (fileSizeInKb > maxSize) {
            Utils.compressedImage(
                context = this,
                file = fileImage,
                uri = getCameraUri(false),
                format = Bitmap.CompressFormat.JPEG,
                quality = 50
            )
        }
        val responseBody = fileImage.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", fileImage.name, responseBody)
        viewModel.postImageToText(body)
    }

    // region camera

    private fun openCamera() {
        cameraResult.launch(getCameraUri(true))
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCameraUri(isCreated: Boolean): Uri {
        if (isCreated) {
            val timestamp = SimpleDateFormat(DATE_FORMAT).format(Date())
            fileImage = File(filesDir, "sawitpro_$timestamp.jpg")
        }
        return FileProvider.getUriForFile(this, "$packageName.provider", fileImage)
    }

    // endregion

    // region permission

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                openCamera()
            } else {
                showToast("Permissions not granted by the user.")
                finish()
            }
        }
    }

    // endregion
}
