package com.mahmoud.android.tobuy.ui.bottomsheet

import android.graphics.Typeface
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.arch.ToBuyViewModel
import com.mahmoud.android.tobuy.databinding.ModelSortOrderItemBinding

class BottomSheetEpoxyController(
    private val selectedSort: ToBuyViewModel.HomeViewState.Sort,
    private val sortOptions: Array<ToBuyViewModel.HomeViewState.Sort>,
    private val selectedCallback: (ToBuyViewModel.HomeViewState.Sort) -> Unit
) : EpoxyController() {

    override fun buildModels() {
        sortOptions.forEach {
            val isSelected = it.name == selectedSort.name
            SortOrderItemEpoxyModel(isSelected, it, selectedCallback).id(it.displayName).addTo(this)
        }
    }

    data class SortOrderItemEpoxyModel(
        val isSelected: Boolean,
        val sort: ToBuyViewModel.HomeViewState.Sort,
        val selectedCallback: (ToBuyViewModel.HomeViewState.Sort) -> Unit
    ): ViewBindingKotlinModel<ModelSortOrderItemBinding>(R.layout.model_sort_order_item) {

        override fun ModelSortOrderItemBinding.bind() {
            textView.text = sort.displayName
            root.setOnClickListener { selectedCallback(sort) }

            textView.typeface = if (isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            view.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }
}