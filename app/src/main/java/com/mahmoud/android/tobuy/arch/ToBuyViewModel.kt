package com.mahmoud.android.tobuy.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.android.tobuy.database.AppDatabase
import com.mahmoud.android.tobuy.database.entity.CategoryEntity
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import com.mahmoud.android.tobuy.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.launch

class ToBuyViewModel : ViewModel() {
    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    val itemWithCategoryEntitiesLiveData = MutableLiveData<List<ItemWithCategoryEntity>>()

    val categoryEntitiesLiveData = MutableLiveData<List<CategoryEntity>>()

    private val _categoriesViewStateLiveData = MutableLiveData<CategoriesViewState>()
    val categoriesViewStateLiveData: LiveData<CategoriesViewState>
        get() = _categoriesViewStateLiveData

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllItemWithCategoryEntities().collect { items ->
                itemWithCategoryEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllCategories().collect { categories ->
                categoryEntitiesLiveData.postValue(categories)
            }
        }
    }

    fun onCategorySelected(categoryId: String) {
        val loadingViewState = CategoriesViewState(isLoading = true)
        _categoriesViewStateLiveData.value = loadingViewState

        val categories = categoryEntitiesLiveData.value ?: return
        val viewStateItemList = ArrayList<CategoriesViewState.Item>()
        categories.forEach {
            viewStateItemList.add(CategoriesViewState.Item(
                categoryEntity = it,
                isSelected = it.id == categoryId
            ))
        }

        val viewState = CategoriesViewState(itemList = viewStateItemList)
        _categoriesViewStateLiveData.postValue(viewState)
    }

    data class CategoriesViewState(
        val isLoading: Boolean = false,
        val itemList: List<Item> = emptyList()
    ) {
        data class Item(
            val categoryEntity: CategoryEntity = CategoryEntity(),
            val isSelected: Boolean = false
        )
    }

    // region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)
            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }
    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)
            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion ItemEntity

    // region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity
}