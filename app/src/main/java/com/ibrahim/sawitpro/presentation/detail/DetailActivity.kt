package com.ibrahim.sawitpro.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ibrahim.sawitpro.R
import com.ibrahim.sawitpro.data.model.request.FormDataRequest
import com.ibrahim.sawitpro.databinding.ActivityDetailBinding
import com.ibrahim.sawitpro.utils.Utils
import com.ibrahim.sawitpro.utils.ext.showLoading
import com.ibrahim.sawitpro.utils.ext.showToast

class DetailActivity : AppCompatActivity() {

    // region initialization

    companion object {
        const val FORM_DATA_CHILD = "form_data"
        private const val EXTRA_TEXT = "EXTRA_TEXT"
        private const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
        fun start(context: Context, textResult: String, imageUri: String) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_TEXT, textResult)
                putExtra(EXTRA_IMAGE_URI, imageUri)
            })
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var formDataRef: DatabaseReference

    private val imageUri: Uri? by lazy { Uri.parse(intent?.extras?.getString(EXTRA_IMAGE_URI)) }
    private val textResult: String? by lazy { intent?.extras?.getString(EXTRA_TEXT) }

    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            imgResult.setImageURI(imageUri)
            etResult.setText(textResult)
            btnSave.setOnClickListener {
                binding.pbLoading.showLoading(true)
                sendDataToFirebase()
            }
        }
    }

    // region setup firebase

    private fun initFirebase() {
        db = Firebase.database
        formDataRef = db.reference.child(FORM_DATA_CHILD)
    }

    private fun sendDataToFirebase() {
        val textParsedResult = binding.etResult.text.toString()
        val formData = FormDataRequest(
            textResult = textParsedResult,
            date = Utils.getCurrentDate(),
            imageUri = imageUri.toString()
        )
        formDataRef.push().setValue(formData) { error, _ ->
            binding.pbLoading.showLoading(false)
            if (error != null) {
                showToast(getString(R.string.error_send_data))
            } else {
                showToast(getString(R.string.label_success_send_data))
                finish()
            }
        }
    }

    // endregion
}
