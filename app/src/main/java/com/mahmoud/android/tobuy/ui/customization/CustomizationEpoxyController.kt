package com.mahmoud.android.tobuy.ui.customization

import android.app.AlertDialog
import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.SharedPrefUtil
import com.mahmoud.android.tobuy.addHeaderModel
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.databinding.ModelCategoryBinding
import com.mahmoud.android.tobuy.databinding.ModelEmptyButtonBinding
import com.mahmoud.android.tobuy.databinding.ModelPriorityColorItemBinding

class CustomizationEpoxyController(
    private val customizationInterface: CustomizationInterface
) : EpoxyController() {
    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it, customizationInterface).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", customizationInterface)
            .id("add_category")
            .addTo(this)

        addHeaderModel("Priorities")

        val highPriorityColor = SharedPrefUtil.getHighPriorityColor()
        val mediumPriorityColor = SharedPrefUtil.getMediumPriorityColor()
        val lowPriorityColor = SharedPrefUtil.getLowPriorityColor()

        PriorityColorItemEpoxyModel("High", highPriorityColor, customizationInterface)
            .id("priority_high")
            .addTo(this)
        PriorityColorItemEpoxyModel("Medium", mediumPriorityColor, customizationInterface)
            .id("priority_medium")
            .addTo(this)
        PriorityColorItemEpoxyModel("Low", lowPriorityColor, customizationInterface)
            .id("priority_low")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name

            root.setOnClickListener {
                customizationInterface.onCategorySelected(categoryEntity)
            }

            root.setOnLongClickListener {
                AlertDialog.Builder(it.context)
                    .setTitle("Delete ${categoryEntity.name}?")
                    .setPositiveButton("Yes") { _, _ ->
                        customizationInterface.onDeleteCategory(categoryEntity)
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
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { customizationInterface.onCategoryEmptyStateClicked() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class PriorityColorItemEpoxyModel(
        val displayText: String,
        val displayColor: Int,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelPriorityColorItemBinding>(R.layout.model_priority_color_item) {

        override fun ModelPriorityColorItemBinding.bind() {
            textView.text = displayText
            root.setStrokeColor(ColorStateList.valueOf(displayColor))
            imageView.setBackgroundColor(displayColor)
            imageView.setOnClickListener { customizationInterface.onPrioritySelected(displayText) }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}