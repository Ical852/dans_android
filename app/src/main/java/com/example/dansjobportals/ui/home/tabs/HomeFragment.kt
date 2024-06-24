package com.example.dansjobportals.ui.home.tabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        loadingInit()
        jobListInit()
        jobFilterInit()
        infiniteScrollInit()
    }

    // Loading Listener
    private fun loadingInit() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.rvJobList.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.rvJobList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    // Job List Setup
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

    // Job List Filter Behavior
    private fun jobFilterInit() {
        // Show Input Toggler
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

        // Input Listener
        binding.fulltimeToggle.setOnCheckedChangeListener { _, isChecked ->
            viewModel.fullTime.postValue(isChecked)
        }
        binding.etLocation.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.location.postValue(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.description.postValue(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        // Filter submission listener
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.jobList.postValue(arrayListOf())
                refreshData()
                closeKeyboard()
                true
            } else {
                false
            }
        }
        binding.btnFilter.setOnClickListener {
            refreshData()
            viewModel.showFilter.postValue(false)
            closeKeyboard()
        }
    }

    // Infinite Scroll Setup
    private fun onScrolledToBottom() {
        if (viewModel.maxPage.value!!.not()) {
            viewModel.page.postValue(viewModel.page.value!! + 1)
            viewModel.fetch("extend")
        }
    }
    private fun infiniteScrollInit() {
        binding.rvJobList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem == totalItemCount - 1) {
                    onScrolledToBottom()
                }
            }
        })
    }

    // Data Refresh
    private fun refreshData(type: String = "normal") {
        viewModel.page.postValue(1)
        viewModel.maxPage.postValue(false)
        viewModel.fetch(type)
    }

    // Keyboard Close
    private fun closeKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}