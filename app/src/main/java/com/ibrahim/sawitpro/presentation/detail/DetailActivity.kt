package com.ibrahim.sawitpro.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ibrahim.sawitpro.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
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

    private val imageUri: Uri? by lazy { Uri.parse(intent?.extras?.getString(EXTRA_IMAGE_URI)) }
    private val textResult: String? by lazy { intent?.extras?.getString(EXTRA_TEXT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            imgResult.setImageURI(imageUri)
            etResult.setText(textResult)
            btnSave.setOnClickListener {
                // TODO: Save data to firebase realtime
            }
        }
    }
}