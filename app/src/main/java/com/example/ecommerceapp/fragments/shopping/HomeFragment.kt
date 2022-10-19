package com.example.ecommerceapp.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapters.HomeCategoryViewPager2Adapter
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.fragments.categories.*
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf(
            MainCategoryFragment(),
            ChairCategoryFragment(),
            CupboardCategoryFragment(),
            TableCategoryFragment(),
            AccessoryCategoryFragment(),
            FurnitureCategoryFragment(),
        )

        val homeCategoryViewPager2Adapter = HomeCategoryViewPager2Adapter(
            categoriesFragment,
            childFragmentManager,
            lifecycle,
        )

        binding.homeCategoryViewPager2.adapter = homeCategoryViewPager2Adapter

        TabLayoutMediator(binding.tabLayout, binding.homeCategoryViewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessory"
                5 -> tab.text = "Furniture"
            }
        }.attach()
    }
}