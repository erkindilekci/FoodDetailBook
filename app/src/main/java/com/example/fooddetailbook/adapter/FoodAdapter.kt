package com.example.fooddetailbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddetailbook.databinding.FoodRecyclerRowBinding
import com.example.fooddetailbook.model.Food
import com.example.fooddetailbook.util.downloadImage
import com.example.fooddetailbook.util.makePlaceHolder
import com.example.fooddetailbook.view.FoodListFragmentDirections

class FoodAdapter(val foodList: ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.FoodHolder>() {
    class FoodHolder(val binding: FoodRecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val binding = FoodRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val binding = DataBindingUtil.inflate<FoodRecyclerRowBinding>(LayoutInflater.from(parent.context), parent, false)
        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.binding.recyclerFoodName.text = foodList[position].foodName
        holder.binding.recyclerFoodKj.text = foodList[position].foodKj

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.downloadImage(foodList[position].foodImage, makePlaceHolder(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateFoodList(newFoodList: List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}