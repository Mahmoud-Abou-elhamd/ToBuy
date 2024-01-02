package com.mahmoud.android.tobuy.ui.customization

import com.mahmoud.android.tobuy.database.entity.CategoryEntity

interface CategoryEntityInterface {
    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
}