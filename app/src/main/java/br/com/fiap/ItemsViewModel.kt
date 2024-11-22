package br.com.fiap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>

    init {

        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        itemDao = database.itemDao()
        itemsLiveData = itemDao.getAll()
    }


    fun addItem(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = ItemModel(title = title, description = description)
            itemDao.insert(newItem)
        }
    }


    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }
}