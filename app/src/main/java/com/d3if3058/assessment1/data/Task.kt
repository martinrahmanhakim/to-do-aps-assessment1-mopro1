package com.d3if3058.assessment1.data


data class Task(
    val id: Int,
    val judul: String,
    val isi: String,
    val isCompleted: Boolean,
    val priority: String // Menambahkan properti priority
)
