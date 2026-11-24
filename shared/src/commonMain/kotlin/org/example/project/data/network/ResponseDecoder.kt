package org.example.project.data.network

internal expect suspend fun decodeApiResponse(text: String): ApiResponse
