package com.example.responsi1mobileh1d023033.data.network

import com.example.responsi1mobileh1d023033.data.model.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("teams/{id}")
    suspend fun getTeam(
        @Path("id") id: Int,
        @Header("X-Auth-Token") token: String
    ): Response<Team>
}
