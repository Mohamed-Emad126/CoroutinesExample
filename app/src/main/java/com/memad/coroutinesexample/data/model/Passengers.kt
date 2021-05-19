package com.memad.coroutinesexample.data.model

import com.google.gson.annotations.SerializedName

data class Passengers(

	@SerializedName("totalPassengers")
	val totalPassengers: Int,

	@SerializedName("data")
	val data: List<DataItem>,

	@SerializedName("totalPages")
	val totalPages: Int
)

data class DataItem(

	@SerializedName("trips")
	val trips: Int,

	@SerializedName("__v")
	val V: Int,

	@SerializedName("name")
	val name: String,

	@SerializedName("_id")
	val id: String,
)
