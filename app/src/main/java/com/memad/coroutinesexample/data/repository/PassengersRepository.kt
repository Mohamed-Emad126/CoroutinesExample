package com.memad.coroutinesexample.data.repository

import com.memad.coroutinesexample.data.api.ApiClient
import com.memad.coroutinesexample.data.model.Passengers
import com.memad.coroutinesexample.utils.Const.Companion.SIZE_OF_PAGE
import retrofit2.Response


class PassengersRepository(private val client: ApiClient) {

    suspend fun getPassengers(page: Int = 0): Response<Passengers> {
        return client.getPassengers(page, SIZE_OF_PAGE)
    }
}