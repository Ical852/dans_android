package com.example.dansjobportals.services.jobs

import com.example.dansjobportals.models.home.JobModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JobApiClient {

    @GET("positions.json")
    suspend fun fetchJobs(
        @Query("description") description: String,
        @Query("location") location: String,
        @Query("fullTime") fullTime: Boolean,
        @Query("page") page: Int,
    ) : List<JobModel>

    @GET("positions/{id}")
    suspend fun fetchJobDetails(
        @Path("id") id: String,
    ) : JobModel

}