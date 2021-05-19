package com.memad.coroutinesexample.data.api

import com.memad.coroutinesexample.BuildConfig
import com.memad.coroutinesexample.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val retrofit: Retrofit by lazy {

        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        Retrofit.Builder().apply {
            baseUrl(Const.BASE_URL)
            client(client.build())
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    fun <T> createService(client: Class<T>?): T {
        return retrofit.create(client)
    }
}