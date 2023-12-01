package com.mahmoud.android.tobuy.ui.home

import com.mahmoud.android.tobuy.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}