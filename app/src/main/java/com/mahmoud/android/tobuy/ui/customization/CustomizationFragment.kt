package com.mahmoud.android.tobuy.ui.customization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.databinding.FragmentProfileBinding
import com.mahmoud.android.tobuy.ui.BaseFragment

class CustomizationFragment : BaseFragment(), CategoryEntityInterface  {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val customizationEpoxyController = CustomizationEpoxyController(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.epoxyRecyclerView.setController(customizationEpoxyController)

        sharedViewModel.categoryEntitiesLiveData.observe(viewLifecycleOwner) { categoryEntityList ->
            customizationEpoxyController.categories = categoryEntityList
        }
    }

    override fun onCategoryEmptyStateClicked() {
        navigateViaNavGraph(R.id.action_profileFragment_to_addCategoryEntityFragment)
    }

    override fun onDeleteCategory(categoryEntity: CategoryEntity) {
        sharedViewModel.deleteCategory(categoryEntity)
    }

    override fun onCategorySelected(categoryEntity: CategoryEntity) {
        Log.i("ProfileFragment", categoryEntity.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}