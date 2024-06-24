package com.example.dansjobportals.services.jobs

import com.example.dansjobportals.models.home.JobModel
import org.koin.dsl.module

val jobRepository = module {
    factory { JobRepository(get()) }
}

class JobRepository (
    private val api: JobApiClient,
) {
    suspend fun fetch(
        description: String, location: String, fullTime: Boolean, page: Int
    ) : List<JobModel> {
        return api.fetchJobs(
            description,
            location,
            fullTime,
            page
        )
    }

    suspend fun fetchById(
        id: String
    ) : JobModel {
        return api.fetchJobDetails(id)
    }
}