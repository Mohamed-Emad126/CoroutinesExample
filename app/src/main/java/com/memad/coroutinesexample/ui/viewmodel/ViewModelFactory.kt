package com.memad.coroutinesexample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.memad.coroutinesexample.data.api.ApiClient
import com.memad.coroutinesexample.data.repository.PassengersRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiClient: ApiClient):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PassengersViewModel::class.java)){
            return PassengersViewModel(PassengersRepository(apiClient)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}