package com.mahmoud.android.tobuy.arch

import com.mahmoud.android.tobuy.database.AppDatabase
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {
    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    fun getAllItemEntities(): Flow<List<ItemEntity>> = appDatabase.itemEntityDao().getAllItemEntities()

    suspend fun updateItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().update(itemEntity)
    }
}