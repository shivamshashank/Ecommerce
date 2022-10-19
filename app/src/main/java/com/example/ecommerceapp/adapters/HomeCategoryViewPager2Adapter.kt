package com.example.ecommerceapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeCategoryViewPager2Adapter(
    private val categoriesFragment: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = categoriesFragment.size

    override fun createFragment(position: Int): Fragment = categoriesFragment[position]
}