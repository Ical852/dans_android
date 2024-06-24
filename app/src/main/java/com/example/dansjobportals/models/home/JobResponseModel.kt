package com.example.dansjobportals.models.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class JobsResponseModel(
    var jobs: List<JobModel>
)

@Entity(tableName = "tableJob")
data class JobModel (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var type: String,
    var url: String,
    var created_at: String,
    var company: String,
    var company_url: String,
    var location: String,
    var title: String,
    var description: String,
    var how_to_apply: String,
    var company_logo: String,
) : Serializable