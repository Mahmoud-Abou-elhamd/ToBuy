package com.mahmoud.android.tobuy.ui.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import com.mahmoud.android.tobuy.databinding.ModelEmptyStateBinding
import com.mahmoud.android.tobuy.databinding.ModelItemEntityBinding
import com.mahmoud.android.tobuy.ui.epoxy.LoadingEpoxyModel

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
) : EpoxyController() {
    var isLoading: Boolean = true
    set(value) {
        field = value
        if(field){
            requestModelBuild()
        }
    }

    var itemEntityList = ArrayList<ItemEntity>()
    set(value) {
        field = value
        isLoading = false
        requestModelBuild()
    }

    override fun buildModels() {
        if(isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if(itemEntityList.isEmpty()){
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        itemEntityList.forEach { item ->
            ItemEntityEpoxyModel(item, itemEntityInterface).id(item.id).addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity,
        val itemEntityInterface: ItemEntityInterface
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity){
        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.title

            if(itemEntity.description == null){
                descriptionTextView.isGone = true
            }else{
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }

            val colorRes = when(itemEntity.priority){
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }

            priorityTextView.setBackgroundColor(ContextCompat.getColor(root.context, colorRes))
        }

    }

    class EmptyStateEpoxyModel: ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state){
        override fun ModelEmptyStateBinding.bind() {

        }

    }
}

