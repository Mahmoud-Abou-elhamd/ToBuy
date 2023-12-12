package com.mahmoud.android.tobuy.ui.profile

import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.addHeaderModel
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.databinding.ModelCategoryBinding
import com.mahmoud.android.tobuy.databinding.ModelEmptyButtonBinding

class ProfileEpoxyController(
    private val onCategoryEmptyStateClicked: () -> Unit
) : EpoxyController() {
    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", onCategoryEmptyStateClicked)
            .id("add_category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val onClicked: () -> Unit
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { onClicked.invoke() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}