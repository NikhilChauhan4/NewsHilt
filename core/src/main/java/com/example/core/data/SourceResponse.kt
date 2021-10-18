package com.example.core.data

data class SourceResponse(val status: String, val sources: Source)

data class Source(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)