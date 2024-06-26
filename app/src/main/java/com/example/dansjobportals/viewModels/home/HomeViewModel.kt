package com.example.dansjobportals.viewModels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dansjobportals.models.home.JobModel
import com.example.dansjobportals.services.jobs.JobRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val homeViewModel = module {
    factory { HomeViewModel( get() ) }
}

class HomeViewModel(val repository: JobRepository): ViewModel() {
    val loading by lazy { MutableLiveData<Boolean>() }
    val message by lazy { MutableLiveData<String>() }
    val page by lazy { MutableLiveData<Int>(1) }
    val description by lazy { MutableLiveData<String>("") }
    val location by lazy { MutableLiveData<String>("") }
    val fullTime by lazy { MutableLiveData<Boolean>(false) }
    val jobList by lazy { MutableLiveData<List<JobModel>>(arrayListOf()) }
    val showFilter by lazy { MutableLiveData<Boolean>(false) }
    val maxPage by lazy { MutableLiveData<Boolean>(false) }

    init {
        fetch()
    }

    fun fetch(type: String = "normal") {
        loading.value = true;
        viewModelScope.launch {
            try {
                val response = repository.fetch(
                    description.value!!, location.value!!, fullTime.value!!, page.value!!
                )
                var newData = response

                if (type == "extend" && newData.isEmpty().not()) {
                    var setupList: ArrayList<JobModel> = ArrayList()
                    setupList.addAll(jobList.value!!)
                    setupList.addAll(response)
                    newData = setupList
                }

                jobList.value = newData
                loading.value = false
            } catch (e: Exception) {
                if (type == "extend") {
                    maxPage.value = true
                }
                message.value = "Terjadi kesalahan " + e.message
                loading.value = false
            }
        }
    }
}