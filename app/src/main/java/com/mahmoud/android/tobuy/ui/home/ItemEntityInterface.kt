package com.mahmoud.android.tobuy.ui.home

import com.mahmoud.android.tobuy.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onItemSelected(itemEntity: ItemEntity)
}