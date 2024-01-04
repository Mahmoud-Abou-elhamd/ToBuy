package com.mahmoud.android.tobuy.ui.customization

import com.mahmoud.android.tobuy.database.entity.CategoryEntity

interface CustomizationInterface {
    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
    fun onPrioritySelected(priorityName: String)
}