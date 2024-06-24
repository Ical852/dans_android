package com.example.dansjobportals.ui.home.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.FragmentHomeBinding
import com.example.dansjobportals.models.home.JobModel
import com.example.dansjobportals.ui.detail.DetailActivity
import com.example.dansjobportals.viewModels.home.HomeViewModel
import com.example.dansjobportals.viewModels.home.JobAdapter
import org.koin.dsl.module
import org.koin.androidx.viewmodel.ext.android.viewModel

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container,  false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobListInit()
        jobFilterInit()
    }

    private fun jobFilterInit() {
        binding.toggleFilter.setOnClickListener {
            viewModel.showFilter.postValue(viewModel.showFilter.value?.not())
        }

        viewModel.showFilter.observe(viewLifecycleOwner, Observer { isShowFilter ->
            if (isShowFilter) {
                binding.filterContainer.visibility = View.VISIBLE
                binding.toggleFilter.setImageResource(R.drawable.ic_arrow_up)
            } else {
                binding.filterContainer.visibility = View.GONE
                binding.toggleFilter.setImageResource(R.drawable.ic_arrow_down)
            }
        })
    }

    private val jobListAdapter by lazy {
        JobAdapter(arrayListOf(), object : JobAdapter.OnAdapterListener{
            override fun onClick(jobModel: JobModel) {
                startActivity(
                    Intent(requireActivity(), DetailActivity::class.java)
                        .putExtra("intent_detail", jobModel)
                )
            }
        })
    }

    private fun jobListInit() {
        binding.rvJobList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJobList.adapter = jobListAdapter
        viewModel.jobList.observe(viewLifecycleOwner, Observer {
            jobListAdapter.add(it)
        })
    }
}