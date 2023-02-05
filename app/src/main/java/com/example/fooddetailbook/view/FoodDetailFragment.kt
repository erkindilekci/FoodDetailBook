package com.example.fooddetailbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.fooddetailbook.databinding.FragmentFoodDetailBinding
import com.example.fooddetailbook.util.downloadImage
import com.example.fooddetailbook.util.makePlaceHolder
import com.example.fooddetailbook.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FoodDetailViewModel

    private var foodId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }

        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoomData(foodId)

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.foodLivaData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.foodName.text = it.foodName
                binding.foodKj.text = it.foodKj
                binding.foodCarbohydrate.text = it.foodCarbohydrate
                binding.foodProtein.text = it.foodProtein
                binding.foodFat.text = it.foodFat
                if (context != null) {
                    binding.foodImageView.downloadImage(it.foodImage, makePlaceHolder(requireContext()))
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
