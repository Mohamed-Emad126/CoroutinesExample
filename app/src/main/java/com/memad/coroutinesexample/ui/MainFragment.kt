package com.memad.coroutinesexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.memad.coroutinesexample.data.Status
import com.memad.coroutinesexample.data.api.ApiClient
import com.memad.coroutinesexample.data.api.ServiceGenerator
import com.memad.coroutinesexample.databinding.FragmentMainBinding
import com.memad.coroutinesexample.ui.viewmodel.PassengersViewModel
import com.memad.coroutinesexample.ui.viewmodel.ViewModelFactory

class MainFragment : Fragment() {
    private var fragmentBinding: FragmentMainBinding? = null
    private lateinit var binding: FragmentMainBinding
    private val passengersViewModel: PassengersViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ServiceGenerator.createService(ApiClient::class.java))
        ).get(PassengersViewModel::class.java)
    }
    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        fragmentBinding = binding
        setupViewModel()
        observeViewModel()

        binding.loadMoreButton.setOnClickListener {
            loadMore()
        }

        return binding.root
    }

    private fun loadMore() {
        if(page == passengersViewModel.lastPage.value){
            Toast.makeText(context, "No more data!", Toast.LENGTH_SHORT).show()
            return
        }
        passengersViewModel.getPassengers(page+1)
    }

    private fun observeViewModel() {
        passengersViewModel.passengersLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> binding.textView.text = it.data!!.size.toString()
                Status.ERROR -> binding.textView.text = it.msg
                Status.LOADING -> binding.textView.text = "Loading..."
            }
        })
        passengersViewModel.currentPage.observe(viewLifecycleOwner, {
            page = it
        })
    }

    private fun setupViewModel() {
        passengersViewModel.getPassengers(0)
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}