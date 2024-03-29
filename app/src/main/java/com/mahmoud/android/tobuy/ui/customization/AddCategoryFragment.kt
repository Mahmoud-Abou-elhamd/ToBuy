package com.mahmoud.android.tobuy.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.databinding.FragmentAddCategoryBinding
import com.mahmoud.android.tobuy.ui.BaseFragment
import java.util.*

class AddCategoryFragment : BaseFragment() {
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveCategoryToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                navigateUp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mainActivity.hideKeyboard(requireView())
    }

    private fun saveCategoryToDatabase() {
        val categoryName = binding.categoryNameEditText.text.toString().trim()
        if (categoryName.isEmpty()) {
            binding.categoryNameTextField.error = "* Required field"
            return
        }

        val categoryEntity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        sharedViewModel.insertCategory(categoryEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}