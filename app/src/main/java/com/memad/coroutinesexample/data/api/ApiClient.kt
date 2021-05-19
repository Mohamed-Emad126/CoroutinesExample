package com.memad.coroutinesexample.data.api

import com.memad.coroutinesexample.data.model.Passengers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("passenger")
    suspend fun getPassengers(@Query("page") page: Int,
                              @Query("size") size: Int) : Response<Passengers>

}