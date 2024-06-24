package com.example.dansjobportals.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.core.text.HtmlCompat
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.ActivityDetailBinding
import com.example.dansjobportals.models.home.JobModel

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jobModel = intent.getSerializableExtra("intent_detail") as? JobModel

        binding.backButton.setOnClickListener {
            finish()
        }

        if (jobModel != null) {
            binding.jobTitle.text = jobModel.title
            binding.jobCompany.text = jobModel.company
            binding.jobLocation.text = jobModel.location
            binding.published.text = "Published : " + jobModel.created_at.substring(0, 20)
            binding.description.text = HtmlCompat.fromHtml(jobModel.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

            binding.applyButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(jobModel.company_url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setPackage("com.android.chrome") // Package name untuk Chrome

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Jika Chrome tidak terinstal, fallback ke browser default
                    intent.setPackage(null)
                    startActivity(intent)
                }
            }
        }
    }
}