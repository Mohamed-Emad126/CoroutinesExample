package com.memad.coroutinesexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memad.coroutinesexample.data.Resource
import com.memad.coroutinesexample.data.model.DataItem
import com.memad.coroutinesexample.data.repository.PassengersRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PassengersViewModel(private val passengersRepository: PassengersRepository) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        passengersLiveData.postValue(Resource.error("${e.message}", items.value))
    }

    val passengersLiveData = MutableLiveData<Resource<ArrayList<DataItem>>>()
    private val items = MutableLiveData<ArrayList<DataItem>>()
    val currentPage = MutableLiveData(0)
    val lastPage = MutableLiveData(Int.MAX_VALUE)

    init {
        items.value = ArrayList()
    }

    fun getPassengers(page: Int = 0) {
        currentPage.postValue(page)
        passengersLiveData.postValue(Resource.loading(items.value))
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val passengersResponse = passengersRepository.getPassengers(page)
            if (passengersResponse.isSuccessful) {
                items.value?.addAll(passengersResponse.body()!!.data)
                withContext(Dispatchers.Main) {
                    lastPage.value = passengersResponse.body()!!.totalPages
                }
                passengersLiveData.postValue(Resource.success(items.value))
            } else {
                passengersLiveData.postValue(
                    Resource.error(
                        "Error: ${passengersResponse.message()}",
                        items.value
                    )
                )
            }
        }
    }

}