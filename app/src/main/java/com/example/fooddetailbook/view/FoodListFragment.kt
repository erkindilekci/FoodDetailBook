package com.example.fooddetailbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddetailbook.adapter.FoodAdapter
import com.example.fooddetailbook.databinding.FragmentFoodListBinding
import com.example.fooddetailbook.viewmodel.FoodListViewModel


class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FoodListViewModel
    private val recyclerFoodAdapter = FoodAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerFoodAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.loadingBar.visibility = View.VISIBLE
            binding.foodWarningText.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE

            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerView.visibility = View.VISIBLE
                recyclerFoodAdapter.updateFoodList(it)
            }
        })

        viewModel.foodWarningMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.foodWarningText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.foodWarningText.visibility = View.GONE
                }
            }
        })

        viewModel.foodLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.foodWarningText.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                } else {
                    binding.loadingBar.visibility = View.GONE
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}