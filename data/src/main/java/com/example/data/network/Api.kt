package com.example.data.network

import com.example.data.network.response.ElementsResponse
import retrofit2.http.GET

interface Api {

    @GET("/v1/360d5306-3918-46ff-963d-db47b563c327")
    suspend fun getData(): ElementsResponse
}