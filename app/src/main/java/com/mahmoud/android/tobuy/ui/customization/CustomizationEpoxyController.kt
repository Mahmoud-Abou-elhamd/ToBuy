package com.mahmoud.android.tobuy.ui.customization

import android.app.AlertDialog
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.addHeaderModel
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.databinding.ModelCategoryBinding
import com.mahmoud.android.tobuy.databinding.ModelEmptyButtonBinding

class CustomizationEpoxyController(
    private val categoryEntityInterface: CategoryEntityInterface
) : EpoxyController() {
    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it, categoryEntityInterface).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", categoryEntityInterface)
            .id("add_category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity,
        val categoryEntityInterface: CategoryEntityInterface
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name

            root.setOnClickListener {
                categoryEntityInterface.onCategorySelected(categoryEntity)
            }

            root.setOnLongClickListener {
                AlertDialog.Builder(it.context)
                    .setTitle("Delete ${categoryEntity.name}?")
                    .setPositiveButton("Yes") { _, _ ->
                        categoryEntityInterface.onDeleteCategory(categoryEntity)
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val categoryEntityInterface: CategoryEntityInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { categoryEntityInterface.onCategoryEmptyStateClicked() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}