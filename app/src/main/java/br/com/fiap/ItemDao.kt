package br.com.fiap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.ItemModel

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemModel")
    fun getAll(): LiveData<List<ItemModel>>
    @Insert
    fun insert(item: ItemModel)
    @Delete
    fun delete(item: ItemModel)
}