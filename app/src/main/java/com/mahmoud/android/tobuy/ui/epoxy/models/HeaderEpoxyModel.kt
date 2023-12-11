package com.mahmoud.android.tobuy.ui.epoxy.models

import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.databinding.ModelHeaderItemBinding

data class HeaderEpoxyModel(
    val headerText: String
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {
    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }
}