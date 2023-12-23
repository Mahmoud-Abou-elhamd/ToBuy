package com.mahmoud.android.tobuy.ui.add

import android.content.res.ColorStateList
import android.graphics.Typeface
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.arch.ToBuyViewModel
import com.mahmoud.android.tobuy.databinding.ModelCategoryItemSelectionBinding
import com.mahmoud.android.tobuy.getAttrColor
import com.mahmoud.android.tobuy.ui.epoxy.LoadingEpoxyModel

class CategoryViewStateEpoxyController(
    private val onCategorySelected: (String) -> Unit,
) : EpoxyController() {
    var viewState = ToBuyViewModel.CategoriesViewState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (viewState.isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        viewState.itemList.forEach { item ->
            CategoryViewStateItem(item, onCategorySelected).id(item.categoryEntity.id).addTo(this)
        }
    }

    data class CategoryViewStateItem(
        val item: ToBuyViewModel.CategoriesViewState.Item,
        private val onCategorySelected: (String) -> Unit,
    ): ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection) {

        override fun ModelCategoryItemSelectionBinding.bind() {

            textView.text = item.categoryEntity.name
            root.setOnClickListener { onCategorySelected(item.categoryEntity.id) }

            val colorRes = if (item.isSelected) R.attr.colorSecondary else R.attr.colorOnSurface
            val color = root.getAttrColor(colorRes)
            textView.setTextColor(color)
            textView.typeface = if (item.isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            root.setStrokeColor(ColorStateList.valueOf(color))
        }
    }
}