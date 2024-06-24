package com.example.dansjobportals.viewModels.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dansjobportals.databinding.JobCardBinding
import com.example.dansjobportals.models.home.JobModel

class JobAdapter(
    private val jobs: ArrayList<JobModel>,
    private val listener: OnAdapterListener
): RecyclerView.Adapter<JobAdapter.ViewHolder>() {
    interface OnAdapterListener {
        fun onClick(article: JobModel)
    }
    class ViewHolder(val binding: JobCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        JobCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: JobAdapter.ViewHolder, position: Int) {
        val job = jobs[position]
        holder.binding.jobTitle.text = job.title
        holder.binding.companyTitle.text = job.company
        holder.binding.cityTitle.text = job.location
        holder.itemView.setOnClickListener {
            listener.onClick(job)
        }
    }

    override fun getItemCount() = jobs.size

    @SuppressLint("NotifyDataSetChanged")
    fun add(data: List<JobModel>) {
        jobs.clear()
        jobs.addAll(data)
        notifyDataSetChanged()
    }
}