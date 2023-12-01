package com.mahmoud.android.tobuy.ui.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import com.mahmoud.android.tobuy.databinding.ModelItemEntityBinding

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
            return
        }

        if(itemEntityList.isEmpty()){
            return
        }

        itemEntityList.forEach { item ->
            ItemEntityEpoxyModel(item, itemEntityInterface).id(item.id).addTo(this)
        }
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

        deleteImageView.setOnClickListener {
            itemEntityInterface.onDeleteItemEntity(itemEntity)
        }

        priorityTextView.setOnClickListener {
            itemEntityInterface.onBumpPriority(itemEntity)
        }
    }

}