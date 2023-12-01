package com.mahmoud.android.tobuy.arch

import com.mahmoud.android.tobuy.database.AppDatabase
import com.mahmoud.android.tobuy.database.entity.ItemEntity

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {
    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItemEntities() = appDatabase.itemEntityDao().getAllItemEntities()
}