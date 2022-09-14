package com.example.coffeeapp.ui.fargment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.adapters.CoffeeHomeAdapter
import com.example.coffeeapp.data.local.CoffeeDatabase
import com.example.coffeeapp.databinding.FragmentHomeBinding
import com.example.coffeeapp.models.coffee.Coffee
import com.example.coffeeapp.repository.CoffeeRepository
import com.example.coffeeapp.ui.HomeActivity
import com.example.coffeeapp.ui.viewmodel.CoffeeViewModel
import com.example.coffeeapp.ui.viewmodel.CoffeeViewModelProviderFactory

class HomeFragment : Fragment() {
    lateinit var adapter: CoffeeHomeAdapter
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: CoffeeViewModel
    // private val viewModel:CoffeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var list = listOf(
//            Coffee("Robusta"),
//            Coffee("Americano"),
//            Coffee("Espresso"),
//            Coffee("Doppio"),
//            Coffee("Cortado"),
//            Coffee("Galão"),
//            Coffee(" Lungo"),
//
//        )


        val repository = CoffeeRepository()
        val viewModelFactory = CoffeeViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CoffeeViewModel::class.java)

        val layoutManager = GridLayoutManager(context, 2)
        adapter = CoffeeHomeAdapter()
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = layoutManager

        viewModel.coffeeLiveData.observe(viewLifecycleOwner) { list ->

            adapter.differ.submitList(list)
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("coffee", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_mainFragment,
                bundle
            )


        }


    }


}